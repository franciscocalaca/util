package com.franciscocalaca.util;

public class FalhaException extends RuntimeException{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FalhaException() {
    }

    public FalhaException(String message) {
        super(message);
    }

    public FalhaException(String message, Throwable cause) {
        super(message, cause);
    }

    public FalhaException(Throwable cause) {
        super(cause);
    }


}
