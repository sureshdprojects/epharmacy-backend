package com.epharmacy.medicine.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class UserOrders {
	private String email;
	private List<Order> userOrders;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Order> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(List<Order> userOrders) {
		this.userOrders = userOrders;
	}

}
