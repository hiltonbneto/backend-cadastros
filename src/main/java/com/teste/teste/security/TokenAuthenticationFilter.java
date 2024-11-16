package com.teste.teste.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.teste.teste.exception.LoginException;
import com.teste.teste.login.service.JwtID;
import com.teste.teste.login.service.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtTokenProvider;

	private final HandlerExceptionResolver handlerExceptionResolver;

	public TokenAuthenticationFilter(final JwtProvider jwtProvider,
			final HandlerExceptionResolver handlerExceptionResolver) {
		this.jwtTokenProvider = Objects.requireNonNull(jwtProvider);
		this.handlerExceptionResolver = Objects.requireNonNull(handlerExceptionResolver);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			var authHeader = request.getHeader("Authorization");
			if (!Objects.isNull(authHeader) && authHeader.startsWith("Bearer")) {
				final String tokenJWT = authHeader.replace("Bearer ", "");
				final boolean tokenValido = jwtTokenProvider.tokenJWTValido(tokenJWT, JwtID.fromRequest(request));
				if (tokenValido) {
					final String usuarioSubject = jwtTokenProvider.getSubject(tokenJWT);
					final Usuario usuario = new Usuario(usuarioSubject, "senha");
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							usuario.getUsername(), null, usuario.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					throw new LoginException("Token invalido");
				}
			}
			filterChain.doFilter(request, response);
		} catch (LoginException e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}
}