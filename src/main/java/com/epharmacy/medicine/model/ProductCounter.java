package com.epharmacy.medicine.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "productCounter")
public class ProductCounter {
	
	private String _id;
	private long seq;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}

}
