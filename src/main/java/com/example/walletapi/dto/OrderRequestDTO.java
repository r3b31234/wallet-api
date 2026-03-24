package com.example.walletapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {

	@NotNull
	private Long customerId;
	
	@NotEmpty
	private List<OrderItemRequestDTO> items;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<OrderItemRequestDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequestDTO> items) {
		this.items = items;
	}
}
