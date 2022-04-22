package com.epharmacy.medicine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epharmacy.medicine.model.Product;
import com.epharmacy.medicine.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/saveproducts")
	public ResponseEntity<Object> saveProducts(@RequestBody List<Product> products) {
		return productService.saveProducts(products);
	}

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/products/{category}/")
	public ResponseEntity<Object> getProductsOfCategory(@PathVariable String category) {
		return productService.getProductsOfCategory(category);
	}

	@GetMapping("/products/{category}/{subCategory}")
	public ResponseEntity<Object> getProductsOfSubCategory(@PathVariable String category, @PathVariable String subCategory) {
		return productService.getProductsOfSubCategory(category, subCategory);
	}
	
}
