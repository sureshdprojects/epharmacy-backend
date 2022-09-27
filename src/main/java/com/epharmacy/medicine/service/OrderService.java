package com.epharmacy.medicine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epharmacy.medicine.model.Order;
import com.epharmacy.medicine.model.UserOrders;
import com.epharmacy.medicine.repository.OrderRepository;
import com.epharmacy.medicine.response.Message;
import com.epharmacy.medicine.response.ResponseHandler;

@Service
public class OrderService {
	
	private String responseMessage;
	private HttpStatus status;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	//generates orderId for every new Order, saves order to user orders
	public ResponseEntity<Object> saveOrderToUserOrders(Order order) {
		
		boolean isOrderSaved = false;
		
			try {
			if(order!=null && order.getEmail() != null && order.getOrderProducts()!= null) {
				
				//generates custom Id for order
				
					long orderSequence = orderRepository.getOrderSequence("orderId");
					order.setOrderId("EPH"+orderSequence);
					try {
						isOrderSaved = orderRepository.saveOrderToUserOrders(order);
						if(isOrderSaved) {
							responseMessage = Message.SUCCESS.getMessage();
							status = HttpStatus.OK;
							cartService.emptyCart(order.getEmail());
						}
						
					}catch(Exception e) {
						responseMessage = Message.INSERTING_INTO_DATABASE_FAILED.getMessage();
						status =HttpStatus.EXPECTATION_FAILED;
					}
					
			}else {
				responseMessage = Message.INPUT_IS_NULL.getMessage();
				status = HttpStatus.BAD_REQUEST;
			}
			}catch(NullPointerException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
				responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
				status = HttpStatus.CONFLICT;
			}
		
		return ResponseHandler.generateResponse(responseMessage, status, isOrderSaved);
	}

	//fetches all orders of users
	public ResponseEntity<Object> getUserOrders(String email) {
		UserOrders userOrders =null;
		if(email!=null) {
			try {
				userOrders = orderRepository.getUserOrders(email);
				if(userOrders == null) {
					responseMessage = Message.USER_DOESNT_HAVE_ANY_ORDERS.getMessage();
					status = HttpStatus.OK;
				}else {
					responseMessage = Message.SUCCESS.getMessage();
					status = HttpStatus.OK;
				}
			}catch(Exception e) {
				e.printStackTrace();
				responseMessage = Message.DATABASE_OPERATION_FAILED.getMessage();
				status = HttpStatus.CONFLICT;
			}
			
		}else {
			responseMessage = Message.EMPTY_REQUEST.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		
		return ResponseHandler.generateResponse(responseMessage, status, userOrders);
	}

}
