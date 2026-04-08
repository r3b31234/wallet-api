package com.example.walletapi.service;

import org.springframework.data.domain.Page;

import com.example.walletapi.domain.Customer;

public interface CustomerService {

	Customer createCustomer(String name, String lastName, String email, Long phoneNumber);
	
	Customer getCustomerById(Long id);
	
	Page<Customer> findCustomers(int page, int size);
	
	Page<Customer> searchCustomers(String name, Boolean active, int page, int size, String sortBy, String direction);
	
	Customer getCustomerByEmail(String email);
	
}
