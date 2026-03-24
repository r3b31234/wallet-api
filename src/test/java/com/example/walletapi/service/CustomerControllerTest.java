package com.example.walletapi.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.walletapi.controller.CustomerController;
import com.example.walletapi.domain.Customer;
import com.example.walletapi.mapper.CustomerMapper;
import com.example.walletapi.security.JwtService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CustomerController.class)
@Import(com.example.walletapi.exception.GlobalExceptionHandler.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

	@MockBean
    private CustomerService customerService;

	@MockBean
    private CustomerMapper customerMapper;
	
	@MockBean
	private JwtService jwtService;

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Luis");
        customer.setEmail("luis@test.com");
        customer.setActive(true);

        when(customerService.createCustomer(anyString(), anyString())).thenReturn(customer);

        when(customerMapper.toDTO(customer)).thenAnswer(invocation -> {
            com.example.walletapi.dto.CustomerResponseDTO dto =
                    new com.example.walletapi.dto.CustomerResponseDTO();
            dto.setId(1L);
            dto.setName("Luis");
            dto.setEmail("luis@test.com");
            dto.setActive(true);
            return dto;
        });

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Luis",
                          "email": "luis@test.com"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Luis"))
                .andExpect(jsonPath("$.email").value("luis@test.com"))
                .andExpect(jsonPath("$.active").value(true));
    }
}