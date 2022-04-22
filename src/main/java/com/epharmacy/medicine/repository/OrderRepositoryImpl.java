package com.epharmacy.medicine.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.epharmacy.medicine.model.Order;
import com.epharmacy.medicine.model.OrderCounter;
import com.epharmacy.medicine.model.UserOrders;
import com.mongodb.client.result.UpdateResult;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	/* pushes new order into user orders array if already exist 
	 * else creates new document and inserts the order*/
	@Override
	public boolean saveOrderToUserOrders(Order order) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(order.getEmail()));
		Update update = new Update();
		update.push("userOrders",order);
		UpdateResult upsert = mongoTemplate.upsert(query, update, UserOrders.class);
		return upsert.wasAcknowledged();
	}

//	increments order sequence by one and returns the sequence
	@Override
	public long getOrderSequence(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.inc("orderSeq",1);
		mongoTemplate.updateFirst(query, update, OrderCounter.class);
		OrderCounter result = mongoTemplate.findOne(query, OrderCounter.class);
		if(result!=null)
			return result.getOrderSeq();
		else
			return -108;
		
	}

	@Override
	public UserOrders getUserOrders(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		return mongoTemplate.findOne(query, UserOrders.class);
	}
	
	

}
