package br.campotech.common.recuperarsenha;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/recuperar-senha")
public class RecuperarSenhaRest {
    @Autowired
    private RecuperarSenhaBo recuperarSenhaBo;

    @Autowired
    private ConviteLoginBo conviteLoginBo;

    @PostMapping
    public void recuperarSenha(@RequestBody RecuperarSenhaDto request) {
        recuperarSenhaBo.restaurarSenha(request.getEmail());
    }

    @GetMapping("/checkId/{resetId}")
    public ConviteLogin checkId(@PathVariable("resetId") UUID resetId) {
        return conviteLoginBo.checkId(resetId);
    }

    @PostMapping("/resetar/{resetId}")
    public Map<String, Object> resetarSenha(@RequestBody Map<String, String> param, @PathVariable("resetId") UUID id) {
        return recuperarSenhaBo.resetarSenha(param, id);
    }
}
