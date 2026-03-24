package com.example.walletapi.mapper;

import org.mapstruct.Mapper;

import com.example.walletapi.domain.Customer;
import com.example.walletapi.dto.CustomerResponseDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerResponseDTO toDTO(Customer customer);
	
}
