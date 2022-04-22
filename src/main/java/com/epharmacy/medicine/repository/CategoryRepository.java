package com.epharmacy.medicine.repository;

import java.util.Collection;
import java.util.List;

import com.epharmacy.medicine.model.Category;


public interface CategoryRepository {
	Collection<Category> saveCategories(List<Category> categories);
	List<Category> getCategories();
	Category getSubCategories(String category);
}
