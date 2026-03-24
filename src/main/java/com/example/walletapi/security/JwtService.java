package com.example.walletapi.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey key = Keys.hmacShaKeyFor("super-secret-key-super-secret-key-123".getBytes());
	
	public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }
	
	public void validateToken(String token) {

	    Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token);
	}
}
