package br.campotech.common.recuperarsenha;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConviteLoginBo {
    @Autowired
    private ConviteLoginDao conviteLoginDao;

    /**
     * Verifica se o convite de login é válido
     * @param id UUID do convite
     * @return ConviteLogin válido
     */
    public ConviteLogin checkId(UUID id) {
        Optional<ConviteLogin> conviteLoginOpt = conviteLoginDao.findById(id);

        if (conviteLoginOpt.isPresent()) {
            ConviteLogin conviteLogin = conviteLoginOpt.get();

            // Verifica se o convite já foi usado
            if (conviteLogin.getUsadoEm() != null) {
                throw new ConviteInvalidoException("O link informado já foi utilizado, solicite uma nova redefinição.");
            }

            // Verifica se o convite está dentro do prazo de validade (1 dia)
            Date dataCriacao = conviteLogin.getData();
            Date dataExpiracao = new Date(dataCriacao.getTime() + (24L * 60L * 60L * 1000L));  // Soma 1 dia

            Date dataAtual = new Date();
            if (dataAtual.after(dataExpiracao)) {
                if (dataAtual.after(dataExpiracao)) {
                    throw new ConviteExpiradoException("O link informado está expirado, solicite uma nova redefinição.");
                }
            }

            return conviteLogin;
        }

        // Convite não encontrado
        throw new MessageErrorException("O link informado é inválido.");
    }
}
