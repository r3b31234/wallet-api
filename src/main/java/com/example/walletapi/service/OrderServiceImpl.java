package com.example.walletapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.walletapi.dto.OrderRequestDTO;
import com.example.walletapi.dto.OrderResponseDTO;
import com.example.walletapi.entity.CustomerEntity;
import com.example.walletapi.entity.OrderEntity;
import com.example.walletapi.entity.OrderItemEntity;
import com.example.walletapi.exception.BusinessException;
import com.example.walletapi.repository.CustomerRepository;
import com.example.walletapi.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;

	public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional // Si falla un item rollback completo 
	public OrderResponseDTO createOrder(OrderRequestDTO request) {
		
		CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));
		
		OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setStatus("CREATED");
        
        double total = 0;
        
        for (var itemDto : request.getItems()) {

            OrderItemEntity item = new OrderItemEntity();
            item.setProduct(itemDto.getProduct());
            item.setPrice(itemDto.getPrice());
            item.setQuantity(itemDto.getQuantity());
            item.setOrder(order);

            total += item.getPrice() * item.getQuantity();

            order.getItems().add(item);
        }
        
        order.setTotal(total);

        OrderEntity saved = orderRepository.save(order);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(saved.getId());
        response.setCustomerId(customer.getId());
        response.setTotal(saved.getTotal());
        response.setStatus(saved.getStatus());

        return response;
	}

	@Override
	@Transactional(readOnly = true) // readOnly se utiliza para indicar que un metodo o clase unicamente realiza operaciones de lectura en base de datos
	public OrderResponseDTO getOrderById(Long id) {

		OrderEntity order = orderRepository.findByIdWithItems(id)
	            .orElseThrow(() -> new BusinessException("Orden no encontrada")); 
			   //orELseThrow -> Si hay un valor presente, devuelve el valor; de lo contrario, genera una excepción producida por la función de suministro de excepciones.
		
		
		OrderResponseDTO response = new OrderResponseDTO();
		response.setId(order.getId());
		response.setCustomerId(order.getCustomer().getId());
		response.setTotal(order.getTotal());
		response.setStatus(order.getStatus());

		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrderResponseDTO> findOrders(String status, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<OrderEntity> orderPage;
		
		if (status != null && !status.isBlank()) {
			orderPage = orderRepository.findByStatus(status, pageable);
		}else {
			orderPage = orderRepository.findAll(pageable);
		}

		return orderPage.map(order -> {
	        OrderResponseDTO dto = new OrderResponseDTO();
	        dto.setId(order.getId());
	        dto.setCustomerId(order.getCustomer().getId());
	        dto.setTotal(order.getTotal());
	        dto.setStatus(order.getStatus());
	        return dto;
	    });
	}

}
