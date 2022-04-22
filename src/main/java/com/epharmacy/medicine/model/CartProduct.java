package com.epharmacy.medicine.model;

import org.springframework.data.annotation.Transient;

public class CartProduct {
	
	private long productId;
	private int quantity;
	@Transient
	private String email;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
