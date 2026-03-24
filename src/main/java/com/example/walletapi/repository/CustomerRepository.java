package com.example.walletapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletapi.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

	Optional<CustomerEntity> findByEmail(String email);
	
	Page<CustomerEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	Page<CustomerEntity> findByActive(boolean active, Pageable pageable);
	
	Page<CustomerEntity> findByNameContainingIgnoreCaseAndActive(String name, boolean active, Pageable pageable);
	
}
