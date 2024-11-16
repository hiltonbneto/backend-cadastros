package com.teste.teste.exception;

public class LoginException extends Exception {

	private static final long serialVersionUID = -2916386870023454356L;

	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public LoginException(String mensagem) {
		super(mensagem);
		this.mensagem = mensagem;
	}

}
