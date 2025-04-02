package br.campotech.common.recuperarsenha;

public class MessageErrorException extends RuntimeException { 
    
    private static final long serialVersionUID = 1L;

	public MessageErrorException() {
		super();
	}

	public MessageErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageErrorException(String message) {
		super(message);
	}

	public MessageErrorException(Throwable cause) {
		super(cause);
	}
}
