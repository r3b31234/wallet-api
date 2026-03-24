package com.example.walletapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.walletapi.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	
	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
	    this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frame -> frame.disable())) // clave para H2
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/customers/**").permitAll()
                .requestMatchers("/orders/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll() //  clave para que no se bloque la cosola de h2
                .anyRequest().authenticated()
        );

    return http.build();
	}
}
