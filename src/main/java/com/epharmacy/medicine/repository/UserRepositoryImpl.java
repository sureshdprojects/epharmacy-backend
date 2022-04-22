package com.epharmacy.medicine.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.epharmacy.medicine.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User saveUser(User user) {
		return mongoTemplate.insert(user, "users");
	}

	@Override
	public User getUserInfo(String userEmail) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(userEmail));
		query.fields().include("password","name","email");
		return mongoTemplate.findOne(query, User.class, "users");
	}

}
