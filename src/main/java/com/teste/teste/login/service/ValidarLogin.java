package com.teste.teste.login.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.teste.teste.login.dto.LoginInput;
import com.teste.teste.login.dto.LoginOutput;

@Component
public class ValidarLogin {

	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	public ValidarLogin(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
	}

	public LoginOutput executar(LoginInput input, Map<String, String> headers) {
		if (Objects.isNull(input) || Objects.isNull(headers) || headers.isEmpty()) {
			return new LoginOutput(null, null, null, false);
		}
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.usuario(), input.senha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtProvider.gerarTokenJWT(input.usuario(), JwtID.fromMap(headers));
		return new LoginOutput(input.usuario(), "Usuario teste", token, true);
	}

}
