package com.teste.teste.login.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import com.teste.teste.categoria.entity.CategoriaEntity;
import com.teste.teste.exception.LoginException;
import com.teste.teste.login.dto.LoginInput;
import com.teste.teste.login.dto.LoginOutput;
import com.teste.teste.login.entity.UsuarioEntity;
import com.teste.teste.login.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ValidarLoginTest {

	@InjectMocks
	ValidarLogin validar;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtProvider jwtProvider;

	@Mock
	private UsuarioRepository repository;

	@Captor
	private ArgumentCaptor<CategoriaEntity> captor;

	@Test
	void naoDeveLogarDadosInvalidos() {
		Assertions.assertThrows(LoginException.class, () -> validar.executar(null, null));
	}

	@Test
	void naoDeveLogarUsuarioInvalido() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Teste", "teste");
		String login = "login";
		BDDMockito.given(repository.findByLogin(login)).willReturn(Optional.empty());
		Assertions.assertThrows(LoginException.class, () -> validar.executar(new LoginInput(login, "senha"), headers));
	}

	@Test
	void naoDeveLogarSenhaInvalida() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Teste", "teste");
		String login = "login";
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setId(0l);
		usuario.setLogin(login);
		usuario.setSenha("aaa");
		BDDMockito.given(repository.findByLogin(login)).willReturn(Optional.of(usuario));
		Assertions.assertThrows(LoginException.class, () -> validar.executar(new LoginInput(login, "senha"), headers));
	}

	@Test
	void deveLogar() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Teste", "teste");
		String login = "login";
		String senha = "senha";
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setId(0l);
		usuario.setLogin(login);
		usuario.setNome("nome");
		usuario.setSenha("senha");
		BDDMockito.given(repository.findByLogin(login)).willReturn(Optional.of(usuario));
		try {
			LoginOutput output = validar.executar(new LoginInput(login, senha), headers);
			Assertions.assertNotNull(output.nome());
		} catch (LoginException e) {
			Assertions.fail();
		}
	}

}
