package com.epharmacy.medicine.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.epharmacy.medicine.model.Product;
import com.epharmacy.medicine.model.ProductCounter;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	//gets all products of particular category from products collection
	@Override
	public List<Product> getProductsOfCategory(String category) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("productCategory").is(category));
		return mongoTemplate.find(query, Product.class, "products");
	}
	
	//inserts all the products/product to products collection
	@Override
	public Collection<Product> saveProducts(List<Product> products) {
		return mongoTemplate.insert(products, "products");
	}

	//gets all products from products collection
	@Override
	public List<Product> getAllProducts() {
		return mongoTemplate.findAll(Product.class,"products");
	}

	//gets products of subcategory of a specific category
	@Override
	public List<Product> getProductsOfSubCategory(String category, String subCategory) {
		Query query = new Query();
		Criteria criteria1 = Criteria.where("productCategory").is(category);
		Criteria criteria2 = Criteria.where("productSubCategory").is(subCategory);
		criteria1.andOperator(criteria2);
		query.addCriteria(criteria1);
		return mongoTemplate.find(query, Product.class, "products");
	}

	//generates custom product sequence
	@Override
	public long getProductSequence(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("productId"));
		Update update = new Update();
		update.inc("seq", 1);
		mongoTemplate.updateFirst(query,update,"productCounter");
		ProductCounter counter = mongoTemplate.findOne(query, ProductCounter.class, "productCounter");
		if(counter != null)
			return counter.getSeq();
		else
			return -108;
	}
}
