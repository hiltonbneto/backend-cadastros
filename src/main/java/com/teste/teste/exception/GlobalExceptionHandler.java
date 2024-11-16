package com.teste.teste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorDTO> handleCustomException(LoginException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorDTO(HttpStatus.FORBIDDEN.value(), ex.getMensagem()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<ErrorDTO> handleCustomException(ValidacaoException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorDTO(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMensagem()),
				HttpStatus.NOT_ACCEPTABLE);
	}

}
