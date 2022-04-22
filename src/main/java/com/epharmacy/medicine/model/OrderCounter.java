package com.epharmacy.medicine.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderCounter")
public class OrderCounter {

	private String _id;
	private long orderSeq;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(long orderSeq) {
		this.orderSeq = orderSeq;
	}

}
