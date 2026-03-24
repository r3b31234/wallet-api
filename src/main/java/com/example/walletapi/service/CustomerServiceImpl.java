package com.example.walletapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.walletapi.domain.Customer;
import com.example.walletapi.entity.CustomerEntity;
import com.example.walletapi.exception.BusinessException;
import com.example.walletapi.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

	@Override
	public Customer createCustomer(String name, String email) {

        CustomerEntity entity = new CustomerEntity();
        entity.setName(name);
        entity.setEmail(email);
        entity.setActive(true);

        CustomerEntity saved = customerRepository.save(entity);

        Customer customer = new Customer();
        customer.setId(saved.getId());
        customer.setName(saved.getName());
        customer.setEmail(saved.getEmail());
        customer.setActive(saved.isActive());

        return customer;
	}

	@Override
	public Customer getCustomerById(Long id) {
		
		CustomerEntity entity = customerRepository.findById(id).orElseThrow(() -> new BusinessException("Cliente no encontrado"));
		
		Customer customer = new Customer();
	    customer.setId(entity.getId());
	    customer.setName(entity.getName());
	    customer.setEmail(entity.getEmail());
	    customer.setActive(entity.isActive());

	    return customer;
	}

	@Override
	public Page<Customer> findCustomers(int page, int size) {
		if (page < 0) {
	        throw new BusinessException("page no puede ser menor a 0");
	    }

	    if (size <= 0) {
	        throw new BusinessException("size debe ser mayor a 0");
	    }

	    if (size > 100) {
	        throw new BusinessException("size no puede ser mayor a 100");
	    }
	    
	    Pageable pageable = PageRequest.of(page, size);
	    Page<CustomerEntity> entityPage = customerRepository.findAll(pageable);

	    List<Customer> customers = entityPage.getContent().stream().map(entity -> {
	        Customer customer = new Customer();
	        customer.setId(entity.getId());
	        customer.setName(entity.getName());
	        customer.setEmail(entity.getEmail());
	        customer.setActive(entity.isActive());
	        return customer;
	    }).toList();

	    return new PageImpl<>(customers, pageable, entityPage.getTotalElements());
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new BusinessException("Email requerido");
		}
		
		CustomerEntity entity = customerRepository.findByEmail(email).orElseThrow(() -> new BusinessException("Cliente no encontrado"));
		
		Customer customer = new Customer();
		customer.setId(entity.getId());
		customer.setName(entity.getName());
		customer.setEmail(entity.getEmail());
		customer.setActive(entity.isActive());
		
		return customer;
		
	}

	@Override
	public Page<Customer> searchCustomers(String name, Boolean active, int page, int size, String sortBy,
			String direction) {
		if (page < 0) {
			throw new BusinessException("Page no puede ser igual a 0");
		}
		
		if (size < 0) {
			throw new BusinessException("Size inválido");
		}
		
		Sort sort = direction.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		Page<CustomerEntity> entityPage;

	    boolean hasName = name != null && !name.isBlank();
	    boolean hasActive = active != null;
	    
	    if (hasName && hasActive) {
	        entityPage = customerRepository.findByNameContainingIgnoreCaseAndActive(name, active, pageable);
	    } else if (hasName) {
	        entityPage = customerRepository.findByNameContainingIgnoreCase(name, pageable);
	    } else if (hasActive) {
	        entityPage = customerRepository.findByActive(active, pageable);
	    } else {
	        entityPage = customerRepository.findAll(pageable);
	    }
	    
	    List<Customer> customers = entityPage.getContent().stream().map(entity -> {
	        Customer customer = new Customer();
	        customer.setId(entity.getId());
	        customer.setName(entity.getName());
	        customer.setEmail(entity.getEmail());
	        customer.setActive(entity.isActive());
	        return customer;
	    }).toList();

	    return new PageImpl<>(customers, pageable, entityPage.getTotalElements());
	}

}
