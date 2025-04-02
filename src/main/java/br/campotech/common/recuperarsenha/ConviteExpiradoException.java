package br.campotech.common.recuperarsenha;

public class ConviteExpiradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConviteExpiradoException(String message) {
        super(message);
    }
}
