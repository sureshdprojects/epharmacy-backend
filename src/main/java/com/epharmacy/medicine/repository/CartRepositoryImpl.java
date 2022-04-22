package com.epharmacy.medicine.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.epharmacy.medicine.model.Cart;
import com.epharmacy.medicine.model.CartProduct;
import com.epharmacy.medicine.model.Product;
import com.mongodb.client.result.UpdateResult;

@Repository
public class CartRepositoryImpl implements CartRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	//checks whether the product is already added or not
	@Override
	public boolean isProductAlreadyAdded(CartProduct cartProduct) {
		String email = cartProduct.getEmail();
		long productId = cartProduct.getProductId();
		Criteria criteria1 = Criteria.where("email").is(email);
		Criteria criteria2 = Criteria.where("cartProducts.productId").is(productId);
		criteria1.andOperator(criteria2);
		Query query = new Query();
		query.addCriteria(criteria1);
		Cart cart= mongoTemplate.findOne(query, Cart.class);
		return cart!=null;
	}

	//adds product to user cart 
	@Override
	public boolean addProductToCart(CartProduct cartProduct) {
		String email = cartProduct.getEmail();
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		Update update = new Update();
		update.push("cartProducts", cartProduct);
		UpdateResult upsert = mongoTemplate.upsert(query, update, "carts");
		return upsert.wasAcknowledged();
	}

//gets products from products collection by getting productId's in user cart
//	db.getCollection('carts').aggregate([
//	 {$match: {email: 'sureshdonthula@gmail.com'}},
//	    {$unwind: '$cartProducts'},
//	    {$lookup:
//	        {from:'products',
//	         localField:'cartProducts.productId',
//	         foreignField:'productId',
//	         as:'product'
//	        }
//	    },
//	    {$unwind: '$product'},
//	    {$addFields: {'product.quantity':'$cartProducts.quantity'}},
//	    {$addFields: {'product.total': { $multiply: [ "$product.price", "$product.quantity" ] }}},
//	    {$replaceRoot: {newRoot: '$product'}}
//	    ])
	@Override
	public Document getUserCartProducts(String email) {
		
		MatchOperation match = Aggregation.match(Criteria.where("email").is(email));
		UnwindOperation unwind = Aggregation.unwind("cartProducts");
		LookupOperation lookup = Aggregation.lookup("products", "cartProducts.productId", "productId", "product");
		UnwindOperation unwindProduct = Aggregation.unwind("product");
		AddFieldsOperationBuilder addfieldQuanity = AddFieldsOperation.addField("product.quantity").withValue("$cartProducts.quantity");
		AddFieldsOperation addQuantity = addfieldQuanity.build();
		ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("product");
		Aggregation aggregation = Aggregation.newAggregation(match,unwind,lookup,unwindProduct,addQuantity,replaceRoot);
		return mongoTemplate.aggregate(aggregation, "carts", Product.class).getRawResults();
	}

	@Override
	public boolean updateQuantityOfProduct(String email, long productId, int incrementBy) {
		Query query = new Query();
		Criteria criteria = Criteria.where("email").is(email);
		criteria.andOperator(Criteria.where("cartProducts.productId").is(productId));
		query.addCriteria(criteria);
		Update update = new Update();
		update.inc("cartProducts.$.quantity",incrementBy);
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Cart.class);
		return updateResult.wasAcknowledged();
	}

	
//	db.getCollection('carts')
//	.update(
//	  { email: "sureshdonthula@gmail.com" },
//	  { $pull: { 'cartProducts': { productId: 10001 } } }
//	);
	@Override
	public long deleteCartProductById(String email,CartProduct cartProduct) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		Update update = new Update();
		update.pull("cartProducts",cartProduct);
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Cart.class);
		return updateResult.getModifiedCount();
	}

	@Override
	public boolean emptyCart(String email) {
		Query query = new Query();
		Criteria criteria = Criteria.where("email").is(email).andOperator(Criteria.where("cartProducts").exists(true));
		query.addCriteria(criteria); 
		Update update = new Update();
		update.unset("cartProducts");
		UpdateResult result = mongoTemplate.updateFirst(query, update, Cart.class);
		return result.wasAcknowledged();		
	}	
	
}
