package com.example.walletapi.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String status;
	
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItemEntity> items = new ArrayList<>(); // se agrega el arraylist para que no truene si items va vacio

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public List<OrderItemEntity> getItems() {
		return items;
	}

	public void setItems(List<OrderItemEntity> items) {
		this.items = items;
	}
}
