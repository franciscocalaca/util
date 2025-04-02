package br.campotech.common.recuperarsenha;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franciscocalaca.http.auth.PasswordChange;
import com.franciscocalaca.http.auth.UtilManager;
import com.franciscocalaca.http.utils.UtilHttp;
import com.franciscocalaca.util.Log;
import com.franciscocalaca.util.UtilRecurso;

@Service
public class RecuperarSenhaBo {
    @Autowired
    private ConviteLoginDao conviteLoginDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private Environment env;

    public void restaurarSenha(String email) {
        // Buscar usuário pelo e-mail
        Usuario usuario = usuarioDao.findByLogin(email);

        if (usuario != null) {
            // Criar e salvar ConviteLogin com usadoEm null
            ConviteLogin conviteLogin = new ConviteLogin();
            conviteLogin.setUsuario(usuario);
            conviteLogin.setData(new Date());
            conviteLogin.setUsadoEm(null);

            conviteLogin = conviteLoginDao.save(conviteLogin);  // Salva no banco e garante o ID

            // Enviar email com o link de recuperação
            enviarEmailRecuperacao(email, usuario.getNome(), conviteLogin.getId().toString());
        } else {
            // E-mail não cadastrado - aqui você pode logar ou apenas silenciar
            Log.warn("Tentativa de recuperação de senha para e-mail não cadastrado: " + email);
        }
    }


    private void enviarEmailRecuperacao(String email, String nome, String conviteId) {
        try {
            String conteudo = obterConteudoEmailRecuperacao(nome, conviteId);

            Map<String, Object> map = new HashMap<>();
            map.put("sender", env.getProperty("tracker.email"));  // e-mail de envio
            map.put("subject", "Redefinição de senha " + env.getProperty("tracker.titulo"));
            map.put("message", conteudo);
            map.put("para", email);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);

            UtilHttp.sendPost(
                    "https://parrot.plug.farm/parrot/message/send",
                    new HashMap<>(),
                    new HashMap<>(),
                    "application/json",
                    json,
                    "utf-8"
            );
        } catch (Exception e) {
            Log.error("Erro ao enviar e-mail de recuperação", e);
        }
    }

    private String obterConteudoEmailRecuperacao(String nome, String conviteId) {
        try {
            String urlBase = env.getProperty("tracker.urlRecuperarSenha");
            String urlRecuperacao = urlBase + conviteId;

            VelocityContext context = new VelocityContext();
            context.put("url", urlRecuperacao);
            context.put("userName", nome);
            context.put("titulo", "Sistema Tracker");

            String template = "messageReset.vm";
            InputStream is = UtilRecurso.getInputStreamRecursoDiretorio(template);
            Reader templateReader = new BufferedReader(new InputStreamReader(is));
            StringWriter out = new StringWriter();

            Velocity.evaluate(context, out, "velocity-util", templateReader);
            is.close();

            return out.toString();
        } catch (Exception e) {
            Log.error("Erro ao gerar conteúdo do e-mail de recuperação", e);
            return "Erro ao gerar o conteúdo do e-mail.";
        }
    }

    public Map<String, Object> resetarSenha(Map<String, String> param, UUID id) {

        ConviteLogin conviteLogin = conviteLoginDao.findById(id).get();
        conviteLogin.setUsadoEm(new Date());
        PasswordChange change = new PasswordChange();

        String novaSenha = (String) param.get("novaSenha");

        change.setNewPass(novaSenha);
        change.setLogin(conviteLogin.getUsuario().getLogin());
        conviteLoginDao.save(conviteLogin);
        return resetarSenhaServidorAuth(change);
    }

    public Map<String, Object> resetarSenhaServidorAuth(PasswordChange change) {
        try {
            String urlAuthManager = env.getProperty("campotech.security.urlAuthManager") + "/changeWitoutPass";
            String userStr = env.getProperty("campotech.security.user");
            String passStr = env.getProperty("campotech.security.pass");

            return UtilManager.change(urlAuthManager, userStr, passStr, change);

        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            return null;
        }
    }
}

