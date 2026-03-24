package com.example.walletapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletapi.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>{

}
