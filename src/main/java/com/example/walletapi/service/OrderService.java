package com.example.walletapi.service;

import org.springframework.data.domain.Page;

import com.example.walletapi.dto.OrderRequestDTO;
import com.example.walletapi.dto.OrderResponseDTO;

public interface OrderService {

	OrderResponseDTO createOrder(OrderRequestDTO request);
	
	OrderResponseDTO getOrderById(Long id);
	
	Page<OrderResponseDTO> findOrders(String status, int page, int size);
	
}
