package com.example.CadastroUsuarios.Components;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTTokenProvider {
	
	private final String jwtSecret = "Secret";
	private final int jwtExpirationMs = 86400000;
	
	public String getEmailFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getBearerFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && !bearer.isEmpty()) {
			return bearer.substring(7);
		}
		return null;
	}
	
	public boolean validaJwtToken(String bearer) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(bearer);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public String generateTokenFromEmail(String email) throws InvalidKeyException {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key key() {
	    return Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}

	
	

}