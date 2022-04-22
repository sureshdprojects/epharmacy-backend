package com.epharmacy.medicine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epharmacy.medicine.model.Category;
import com.epharmacy.medicine.service.CategoryService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/getcategories")
	public ResponseEntity<Object> getCategories(){
		return categoryService.getCategories();
	}
	
	@GetMapping("/{category}/subcategories")
	public ResponseEntity<Object> getSubCategories(@PathVariable String category){
		return categoryService.getSubCategories(category);
	}
	
	@PostMapping("/savecategories")
	public ResponseEntity<Object> saveCategories(@RequestBody List<Category> categories){
		return categoryService.validateAndSave(categories);
	}
	
}
