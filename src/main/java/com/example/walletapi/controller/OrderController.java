package com.example.walletapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletapi.dto.OrderRequestDTO;
import com.example.walletapi.dto.OrderResponseDTO;
import com.example.walletapi.dto.PageResponseDTO;
import com.example.walletapi.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO request){
		
		OrderResponseDTO response = orderService.createOrder(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id){
		
		 return ResponseEntity.ok(orderService.getOrderById(id));
	}
	
	@GetMapping
	public ResponseEntity<PageResponseDTO<OrderResponseDTO>> findOrders(
	        @RequestParam(required = false) String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    Page<OrderResponseDTO> result = orderService.findOrders(status, page, size);

	    PageResponseDTO<OrderResponseDTO> response = new PageResponseDTO<>();
	    response.setPage(result.getNumber());
	    response.setSize(result.getSize());
	    response.setTotalElements(result.getTotalElements());
	    response.setTotalPages(result.getTotalPages());
	    response.setData(result.getContent());

	    return ResponseEntity.ok(response);
	}
}
