package com.codirect.codiappapi.security;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.codirect.codiappapi.properties.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {

	@Autowired
	private JwtProperties jwtProperties;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return Jwts.builder()
				.setClaims(buildClaims(userDetails))
				.setIssuedAt(new Date())
				.setExpiration(buildExpirationDate())
				.signWith(buildSigningKey())
				.compact();
	}
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(buildSigningKey().getEncoded()).build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception exception) {
			log.error(exception.getMessage());
			return false;
		}
	}
	
	public String getEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(buildSigningKey().getEncoded()).build()
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	private Map<String, Object> buildClaims(UserDetailsImpl userDetails) {
		LinkedHashMap<String, Object> claims = new LinkedHashMap<>();
		claims.put("id", userDetails.getId());
		claims.put("name", userDetails.getName());
		claims.put("email", userDetails.getEmail());
		claims.put("sub", userDetails.getEmail());
		return claims;
	}
	
	private Date buildExpirationDate() {
		return new Date(new Date().getTime() + jwtProperties.getExpirationTime() * 1000);
	}
	
	private SecretKey buildSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretCode().getBytes(Charset.forName("UTF-8")));
	}
}
