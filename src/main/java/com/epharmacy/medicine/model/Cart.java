package com.epharmacy.medicine.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart {

	private String email;
	private List<CartProduct> cartProducts;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	
}
