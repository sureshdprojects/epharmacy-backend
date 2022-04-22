package com.epharmacy.medicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epharmacy.medicine.model.CartProduct;
import com.epharmacy.medicine.service.CartService;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/addproducttocart")
	public ResponseEntity<Object> addProductToCart(@RequestBody CartProduct cartProduct) {
		return cartService.addProductToCart(cartProduct);
	}

	@GetMapping("/getcartproducts/{email}")
	public ResponseEntity<Object> getUserCartProducts(@PathVariable String email) {
		return cartService.getUserCartProducts(email);
	}
	
	@GetMapping("/updatequantity/{email}/{productId}/{incrementBy}")
	public ResponseEntity<Object> updateQuantityOfProduct(@PathVariable String email,@PathVariable long productId, @PathVariable int incrementBy) {
		return cartService.updateQuantityOfProduct(email,productId,incrementBy);
	}
	
	@PostMapping("/cartproduct")
	public ResponseEntity<Object> deleteCartProduct(@RequestBody CartProduct cartproduct) {
		return cartService.deleteCartProduct(cartproduct);
	}
}
