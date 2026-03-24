package com.example.walletapi.controller;

import java.util.List;

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

import com.example.walletapi.domain.Customer;
import com.example.walletapi.dto.CustomerRequestDTO;
import com.example.walletapi.dto.CustomerResponseDTO;
import com.example.walletapi.dto.PageResponseDTO;
import com.example.walletapi.mapper.CustomerMapper;
import com.example.walletapi.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;
	private final CustomerMapper customerMapper;
	
	public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
		this.customerService = customerService;
		this.customerMapper = customerMapper;
	}
	
	@PostMapping
	public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO request){
		
		Customer customer = customerService.createCustomer(request.getName(), request.getEmail());
		
		CustomerResponseDTO response = customerMapper.toDTO(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {

	    Customer customer = customerService.getCustomerById(id);

	    CustomerResponseDTO response = customerMapper.toDTO(customer);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<PageResponseDTO<CustomerResponseDTO>> findcustomers(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		
		Page<Customer> customerPage = customerService.findCustomers(page, size);
		
		List<CustomerResponseDTO> data = customerPage.getContent()
		        .stream()
		        .map(customerMapper::toDTO)
		        .toList();
		
		PageResponseDTO<CustomerResponseDTO> response = new PageResponseDTO<>();
	    response.setPage(customerPage.getNumber());
	    response.setSize(customerPage.getSize());
	    response.setTotalElements(customerPage.getTotalElements());
	    response.setTotalPages(customerPage.getTotalPages());
	    response.setData(data);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search")
	public ResponseEntity<CustomerResponseDTO> getByEmail(@RequestParam String email){
		
		Customer customer = customerService.getCustomerByEmail(email);
		
		CustomerResponseDTO response = customerMapper.toDTO(customer);

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search-advanced")
	public ResponseEntity<PageResponseDTO<CustomerResponseDTO>> searchAdvanced(
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) Boolean active,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction) {

	    Page<Customer> customerPage = customerService.searchCustomers(name, active, page, size, sortBy, direction);

	    List<CustomerResponseDTO> data = customerPage.getContent()
	            .stream()
	            .map(customerMapper::toDTO)
	            .toList();

	    PageResponseDTO<CustomerResponseDTO> response = new PageResponseDTO<>();
	    response.setPage(customerPage.getNumber());
	    response.setSize(customerPage.getSize());
	    response.setTotalElements(customerPage.getTotalElements());
	    response.setTotalPages(customerPage.getTotalPages());
	    response.setData(data);

	    return ResponseEntity.ok(response);
	}
		
}
