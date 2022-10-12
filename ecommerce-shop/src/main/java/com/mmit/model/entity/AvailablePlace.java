package com.mmit.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "avaliable_place")
public class AvailablePlace implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(nullable = false)
	private String city;
	@JoinColumn(nullable = false)
	private String township;
	@JoinColumn(nullable = false)
	private int deliveryFees;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTownship() {
		return township;
	}
	public void setTownship(String township) {
		this.township = township;
	}
	
	public int getDeliveryFees() {
		return deliveryFees;
	}
	public void setDeliveryFees(int deliveryFees) {
		this.deliveryFees = deliveryFees;
	}
	@Override
	public String toString() {
		return "AvailablePlace [id=" + id + ", city=" + city + ", township=" + township + ", deliveryFees="
				+ deliveryFees + "]";
	}
	
	
	
}
