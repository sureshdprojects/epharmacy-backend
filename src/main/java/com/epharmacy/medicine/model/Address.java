package com.epharmacy.medicine.model;

public class Address {

	private String houseNumber;
	private String streetNameAndVillage;
	private String city;
	private int pincode;
	private String landmark;
	private String state;
	private String country;

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetNameAndVillage() {
		return streetNameAndVillage;
	}

	public void setStreetNameAndVillage(String streetNameAndVillage) {
		this.streetNameAndVillage = streetNameAndVillage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
