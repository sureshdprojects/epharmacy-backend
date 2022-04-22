package com.epharmacy.medicine.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {

	@Id
	private String categoryName;
	private String imageUrl;
	private List<String> subCategories;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<String> subCategories) {
		this.subCategories = subCategories;
	}

}
