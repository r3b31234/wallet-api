package com.example.walletapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletapi.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final JwtService jwtService;

	public AuthController(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String, String> body) {
		
		String username = body.get("username");

		String token = jwtService.generateToken(username);

		return Map.of("token", token);
	}
}
