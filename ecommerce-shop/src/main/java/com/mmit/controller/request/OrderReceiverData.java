package com.mmit.controller.request;

public class OrderReceiverData {
	private String name;
	private String email;
	private String phone;
	private String address;
	private int township;
	
	
	public int getTownship() {
		return township;
	}
	public void setTownship(int township) {
		this.township = township;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "OrderReceiverData [name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", townshipId=" + township + "]";
	}
	
	
}
