package com.teste.teste.login.service;

import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.DigestUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

public class JwtID {
	
	private String host;
	
	private String userAgent;
	
	private String secCHUA;
	
	
	private JwtID() {
		this.host = "";
		this.userAgent = "";
		this.secCHUA = "";
	}
	
	public static JwtID fromMap(final Map<String, String> headers) {
		JwtID jwtID = new JwtID();
		if (!Objects.isNull(headers) && !headers.isEmpty()) {			
			headers.forEach((key, value) -> preencherJwtID(key, value, jwtID));				
		}
		return jwtID;	
	}
	
	public static JwtID fromRequest(final HttpServletRequest request) {
		JwtID jwtID = new JwtID();
		if (!Objects.isNull(request) && !Objects.isNull(request.getHeaderNames())) {			
			Enumeration<String> headers = request.getHeaderNames();
			while (headers.hasMoreElements()) {
				final String key = headers.nextElement();
				final String value = request.getHeader(key);
				preencherJwtID(key, value, jwtID);			
			}
		}
		return jwtID;
	}
	
	private static void preencherJwtID(final String key, final String value, JwtID jwtID) {
		if (key.equalsIgnoreCase("host")) {
			jwtID.host = value;
		}
		if (key.equalsIgnoreCase("user-agent")) {
			jwtID.userAgent = value;
		}
		if (key.equalsIgnoreCase("sec-ch-ua")) {
			jwtID.secCHUA = value;
		}		
	}
	
	public String getHash() {
		String hash = "";
		if (StringUtils.isNotBlank(host) && StringUtils.isNotBlank(userAgent)) {			
			StringBuilder concat = new StringBuilder();
			concat.append(host).append("|").append(userAgent);
			concat.append(secCHUA.length() > 0 ? "|" : "").append(secCHUA);
			hash = DigestUtils.md5DigestAsHex(concat.toString().getBytes());		
		}
		return hash;
	}

}
