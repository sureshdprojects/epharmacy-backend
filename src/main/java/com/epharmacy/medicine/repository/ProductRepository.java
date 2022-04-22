package com.epharmacy.medicine.repository;

import java.util.Collection;
import java.util.List;

import com.epharmacy.medicine.model.Product;

public interface ProductRepository {
	List<Product> getProductsOfCategory(String Category);
	Collection<Product> saveProducts(List<Product> products);
	List<Product> getAllProducts();
	List<Product> getProductsOfSubCategory(String category, String subCategory);
	long getProductSequence(String name);
}
