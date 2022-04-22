package com.epharmacy.medicine.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.epharmacy.medicine.model.Category;


@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Collection<Category> saveCategories(List<Category> categories) {
		return mongoTemplate.insertAll(categories);
	}

	@Override
	public List<Category> getCategories() {
		return mongoTemplate.findAll(Category.class, "categories");
	}

	@Override
	public Category getSubCategories(String category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("categoryName").is(category));
		query.fields().include("subCategories").exclude("_id");
		return mongoTemplate.findOne(query, Category.class);
	}

}
