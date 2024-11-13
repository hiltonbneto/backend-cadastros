package com.teste.teste.login.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 6198679812227865421L;

	private final String nome;
	
	private final String senha;
	
	public Usuario (final String nome, final String senha) {
		this.nome = nome;
		this.senha = new BCryptPasswordEncoder().encode(senha) ;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {		
		return this.senha;
	}

	@Override
	public String getUsername() {		
		return this.nome;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
