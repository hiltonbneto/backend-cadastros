package com.teste.teste.login.entity;

import com.teste.teste.login.dto.UsuarioInput;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "usuario")
public class UsuarioEntity {

	@Id
	@SequenceGenerator(name = "produto_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_seq")
	@Column(name = "id", columnDefinition = "bigserial", updatable = false)
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "login", nullable = false)
	private String login;

	@Column(name = "senha", nullable = false)
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static UsuarioEntity fromUsuarioInput(UsuarioInput input) {
		UsuarioEntity entity = new UsuarioEntity();
		entity.setLogin(input.login());
		entity.setNome(input.nome());
		entity.setSenha(input.senha());
		return entity;
	}

}
