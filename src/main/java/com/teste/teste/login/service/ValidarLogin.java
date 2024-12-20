package com.teste.teste.login.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.teste.teste.exception.LoginException;
import com.teste.teste.login.dto.LoginInput;
import com.teste.teste.login.dto.LoginOutput;
import com.teste.teste.login.entity.UsuarioEntity;
import com.teste.teste.login.repository.UsuarioRepository;

@Component
public class ValidarLogin {

	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	private final UsuarioRepository repository;

	public ValidarLogin(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider,
			final UsuarioRepository repository) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.repository = repository;
	}

	public LoginOutput executar(LoginInput input, Map<String, String> headers) throws LoginException {
		if (Objects.isNull(input) || Objects.isNull(headers) || headers.isEmpty()) {
			throw new LoginException("Usuário ou senha inválidos");
		}
		Optional<UsuarioEntity> entity = repository.findByLogin(input.login());
		if (entity.isEmpty() || Objects.isNull(entity.get()) || !entity.get().getSenha().equals(input.senha())) {
			throw new LoginException("Usuário ou senha inválidos");
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.login(), input.senha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtProvider.gerarTokenJWT(input.login(), JwtID.fromMap(headers));
		return new LoginOutput(entity.get().getNome(), token);
	}

}
