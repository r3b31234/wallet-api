package com.example.walletapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequestDTO {

	@NotBlank(message = "Nombre requerido")
	@Size(max = 120, message = "Nombre máximo de 120 caracteres")
	private String name;
	
	@NotBlank(message = "Email requerido")
    @Email(message = "Email inválido")
    @Size(max = 200, message = "Email máximo 200 caracteres")
    private String email;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
