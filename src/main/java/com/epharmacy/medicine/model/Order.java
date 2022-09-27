package com.epharmacy.medicine.model;

import java.util.List;

import org.springframework.data.annotation.Transient;

public class Order {

	private String orderId;
	private List<Product> orderProducts;
	private double orderValue;
	@Transient
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}
	
	

}
