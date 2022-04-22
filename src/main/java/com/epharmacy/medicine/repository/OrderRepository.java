package com.epharmacy.medicine.repository;

import com.epharmacy.medicine.model.Order;
import com.epharmacy.medicine.model.UserOrders;

public interface OrderRepository {

	boolean saveOrderToUserOrders(Order order);

	long getOrderSequence(String string);

	UserOrders getUserOrders(String email);

}
