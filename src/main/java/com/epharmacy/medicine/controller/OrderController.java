package com.epharmacy.medicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epharmacy.medicine.model.Order;
import com.epharmacy.medicine.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<Object> saveOrdertoUserOrders(@RequestBody Order order) {
		return orderService.saveOrderToUserOrders(order);
	}

	@GetMapping("/orders/{email}")
	public ResponseEntity<Object> getUserOrders(@PathVariable String email) {
		return orderService.getUserOrders(email);
	}
}
