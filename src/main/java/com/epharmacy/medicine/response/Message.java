package com.epharmacy.medicine.response;

public enum Message {
	USER_DOESNT_HAVE_ANY_ORDERS("User do not have any orders yet"),
	PLEASE_ENTER_CREDENTIALS("Please enter credentials"),
	INPUT_IS_NULL("input is null, provide all inputs"),
	EMPTY("empty"),
	EMAIL_ALREADY_EXISTS("email already exists"),
	ACCOUNT_CREATED("user account created"),
	INCORRECT_EMAIL("incorrect email"),
	INCORRECT_PASSWORD("incorrect password"),
	EMPTY_REQUEST("request is empty, please send data"),
	USER_VERIFIED("verification successfull"),
	UNABLE_TO_ADD_PRODUCTS("unable to add products to database"),
	DATABASE_FETCHING_FAILED("Exception from database, when fetching"),
	DATABASE_OPERATION_FAILED("database operation failed"),
	INSERTING_INTO_DATABASE_FAILED("inserting into database failed"),
	USER_NOT_LOGGED_IN("please login to add product to cart"),
	USER_DOES_NOT_EXISTS("invalid user id, user not found"),
	ADDED_TO_CART("product added to cart"),
	PRODUCT_ALREADY_ADDED("product is already added to cart, to increase quantity please go to cart"),
	UNABLE_TO_ADD_TO_CART("unable to add product to cart"),
	CART_IS_EMPTY("user cart is emplty"),
	PRODUCTS_NOT_FOUND("product with given info does not exist"),
	SUCCESS("operation is successfull");
	
	
	private String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


}
