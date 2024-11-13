package com.teste.teste.login;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.teste.teste.login.dto.LoginInput;
import com.teste.teste.login.dto.LoginOutput;
import com.teste.teste.login.service.ValidarLogin;

@RestController
public class LoginController {

	private final ValidarLogin validarLogin;

	public LoginController(final ValidarLogin validarLogin) {
		this.validarLogin = validarLogin;
	}

	@CrossOrigin
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginOutput> login(@RequestBody LoginInput input, @RequestHeader Map<String, String> headers) {
		return ResponseEntity.ok(this.validarLogin.executar(input, headers));
	}

}
