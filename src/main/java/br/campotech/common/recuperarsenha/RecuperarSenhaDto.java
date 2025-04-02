package br.campotech.common.recuperarsenha;

public class RecuperarSenhaDto {
    private String email;

    public String getEmail() {
        return email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

