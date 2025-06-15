package com.unla.tp_oo2_g16.Exceptions;

public class ClienteNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
