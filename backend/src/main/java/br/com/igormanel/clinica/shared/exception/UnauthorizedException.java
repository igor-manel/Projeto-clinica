package br.com.igormanel.clinica.shared.exception;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException() {
		super("Credenciais ausentes ou inválidas.");
	}

}
