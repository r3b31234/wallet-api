package com.example.walletapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.walletapi.domain.Customer;
import com.example.walletapi.entity.CustomerEntity;
import com.example.walletapi.exception.BusinessException;
import com.example.walletapi.repository.CustomerRepository;

public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	public CustomerServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldCreateCustomerSuccessfully() {
		CustomerEntity savedEntity = new CustomerEntity();
		savedEntity.setName("Luis");
		savedEntity.setEmail("luis@test.com");
		savedEntity.setActive(true);

		try {
			var idField = CustomerEntity.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(savedEntity, 1L);
		} catch (Exception e) {
			fail("No se pudo asignar id al entity de prueba");
		}

		when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedEntity);

		Customer result = customerService.createCustomer("Luis", "Espina","luis@test.com", (long) 5566);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Luis", result.getName());
		assertEquals("luis@test.com", result.getEmail());
		assertTrue(result.isActive());

		verify(customerRepository, times(1)).save(any(CustomerEntity.class));
	}

	@Test
	void shouldThrowBusinessExceptionWhenCustomerNotFoundByEmail() {
		when(customerRepository.findByEmail("x@test.com")).thenReturn(Optional.empty());

		BusinessException ex = assertThrows(BusinessException.class,
				() -> customerService.getCustomerByEmail("x@test.com"));

		assertEquals("Cliente no encontrado", ex.getMessage());
	}
}
