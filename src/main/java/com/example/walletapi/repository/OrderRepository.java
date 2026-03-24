package com.example.walletapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.walletapi.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	 //JOIN FETCH traigo la orden y los item de la orden sin hacer mas de un querty
	@Query("SELECT o FROM OrderEntity o JOIN FETCH o.items WHERE o.id = :id")
	Optional<OrderEntity> findByIdWithItems(Long id);

	Page<OrderEntity> findByStatus(String status, Pageable pageable);
	
}
