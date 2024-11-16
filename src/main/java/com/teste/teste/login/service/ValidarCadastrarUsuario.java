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
import com.teste.teste.login.dto.LoginOutput;
import com.teste.teste.login.dto.UsuarioInput;
import com.teste.teste.login.entity.UsuarioEntity;
import com.teste.teste.login.repository.UsuarioRepository;

@Component
public class ValidarCadastrarUsuario {

	private final AuthenticationManager authenticationManager;

	private final JwtProvider jwtProvider;

	private final UsuarioRepository repository;

	public ValidarCadastrarUsuario(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider,
			final UsuarioRepository repository) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.repository = repository;
	}

	public LoginOutput executar(UsuarioInput input, Map<String, String> headers) throws LoginException {
		if (Objects.isNull(input) || Objects.isNull(headers) || headers.isEmpty()) {
			throw new LoginException("Dados inválidos");
		}
		Optional<UsuarioEntity> opUsuario = repository.findByLogin(input.login());
		if (opUsuario.isPresent()) {
			throw new LoginException("Usuário já cadastrado");
		}
		UsuarioEntity entity = UsuarioEntity.fromUsuarioInput(input);
		entity = repository.save(entity);
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.login(), input.senha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String token = jwtProvider.gerarTokenJWT(input.login(), JwtID.fromMap(headers));
		return new LoginOutput(entity.getNome(), token);
	}

}
