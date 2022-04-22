package com.epharmacy.medicine.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epharmacy.medicine.model.CartProduct;
import com.epharmacy.medicine.repository.CartRepository;
import com.epharmacy.medicine.response.Message;
import com.epharmacy.medicine.response.ResponseHandler;

@Service
public class CartService {

	private String responseMessage;
	private HttpStatus status;

	@Autowired
	private CartRepository cartRepository;

	// adds product to cart if it does not exists in user cart
	public ResponseEntity<Object> addProductToCart(CartProduct cartProduct) {

		boolean isProductAdded = false;

		try {
			if (cartProduct == null) {
				responseMessage = Message.EMPTY_REQUEST.getMessage();
				status = HttpStatus.BAD_REQUEST;
			} else {
				isProductAdded = cartRepository.isProductAlreadyAdded(cartProduct);
				if (isProductAdded) {
					responseMessage = Message.PRODUCT_ALREADY_ADDED.getMessage();
					status = HttpStatus.FOUND;
				} else {
					isProductAdded = cartRepository.addProductToCart(cartProduct);
					responseMessage = Message.ADDED_TO_CART.getMessage();
					status = HttpStatus.CREATED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}

		return ResponseHandler.generateResponse(responseMessage, status, isProductAdded);

	}

	// gets products based on productIds in his cart
	public ResponseEntity<Object> getUserCartProducts(String email) {

		Document userCartProducts = null;

		try {
			if (email == null) {
				responseMessage = Message.EMPTY_REQUEST.getMessage();
				status = HttpStatus.BAD_REQUEST;
			} else {
				userCartProducts = cartRepository.getUserCartProducts(email);
				responseMessage = Message.SUCCESS.getMessage();
				status = HttpStatus.OK;
			}
		}  catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}

		return ResponseHandler.generateResponse(responseMessage, status, userCartProducts);
	}

	public ResponseEntity<Object> updateQuantityOfProduct(String email, long productId, int incrementBy) {

		boolean isUpdated = false;

		try {
			if (email != null && productId != 0 && incrementBy != 0) {

				isUpdated = cartRepository.updateQuantityOfProduct(email, productId, incrementBy);
				responseMessage = Message.SUCCESS.getMessage();
				status = HttpStatus.OK;

			} else {
				responseMessage = Message.EMPTY_REQUEST.getMessage();
				status = HttpStatus.BAD_REQUEST;
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}

		return ResponseHandler.generateResponse(responseMessage, status, isUpdated);
	}

	public ResponseEntity<Object> deleteCartProduct(CartProduct cartProduct) {

		long modifiedCount = 0;

		try {
			String email = cartProduct.getEmail();
			if (email != null) {
				modifiedCount = cartRepository.deleteCartProductById(email, cartProduct);
				status = HttpStatus.OK;
				responseMessage = Message.SUCCESS.getMessage();
			}
		} catch (NullPointerException e) {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
			status = HttpStatus.CONFLICT;
		}
		return ResponseHandler.generateResponse(responseMessage, status, modifiedCount);
	}

	public boolean emptyCart(String email) {
		try
		{
		if (email != null) {
			return cartRepository.emptyCart(email);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
