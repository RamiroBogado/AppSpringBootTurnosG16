package com.unla.tp_oo2_g16.Exceptions;

public class ClienteDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
