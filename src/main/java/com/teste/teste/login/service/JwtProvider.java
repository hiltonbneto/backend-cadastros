package com.teste.teste.login.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.micrometer.common.util.StringUtils;

@Component
public class JwtProvider {

	private static final String ISSUER = "API BACK-END";

	@Value("${api.security.token.secret}")
	private String secret;

	@Value("${api.security.token.secondsexpiry}")
	private long secondsExpiry;

	public String gerarTokenJWT(final String usuario, final JwtID jwtID) {
		String tokenJWT = "";
		if (StringUtils.isNotBlank(usuario) && !Objects.isNull(jwtID) && !jwtID.getHash().isEmpty()) {
			var tempoExpirar = LocalDateTime.now().plusSeconds(secondsExpiry).atZone(ZoneId.systemDefault()).toInstant();
			final Algorithm algoritmo = Algorithm.HMAC512(secret);
			tokenJWT = JWT.create().withSubject(usuario).withIssuer(ISSUER).withExpiresAt(tempoExpirar).withJWTId(jwtID.getHash()).sign(algoritmo);
		}
		return tokenJWT;
	}

	public boolean tokenJWTValido(final String tokenJWT, final JwtID jwtID) {
		try {
			final Algorithm algoritmo = Algorithm.HMAC512(secret);
			JWT.require(algoritmo).withIssuer(ISSUER).build().verify(tokenJWT);
			DecodedJWT decodedJWT = JWT.decode(tokenJWT);
			return decodedJWT.getId().equals(jwtID.getHash());
		} catch (JWTVerificationException exception) {
			return false;
		}
	}

	public String getSubject(final String tokenJWT) {
		String subject = "";
		if (StringUtils.isNotBlank(tokenJWT)) {
			try {
				DecodedJWT decodedJWT = JWT.decode(tokenJWT);
				subject = decodedJWT.getSubject();
			} catch (Exception e) {
				// Não exibe mensagem
			}
		}
		return subject;
	}

}
