package com.mmit.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customer;
	@OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	private List<OrderItem> items = new ArrayList<>();
	private String shippingName;
	private String shippingPhone;
	private String shippingEmail;
	private String shippingAddress;
	
	@ManyToOne
	@JoinColumn(name = "shipping_township")
	private AvailablePlace shippingTownship;
	
	@ManyToOne
	@JoinColumn(name = "deliveryMan_id")
	private User deliveryMan;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@CreationTimestamp
	private LocalDateTime created_at;
	@UpdateTimestamp
	private LocalDateTime updated_at;
	public Orders() {
		super();
	}
	public void addOrderItem(OrderItem item) {
		item.setOrder(this);
		items.add(item);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingPhone() {
		return shippingPhone;
	}
	public void setShippingPhone(String shippingPhone) {
		this.shippingPhone = shippingPhone;
	}
	public String getShippingEmail() {
		return shippingEmail;
	}
	public void setShippingEmail(String shippingEmail) {
		this.shippingEmail = shippingEmail;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public AvailablePlace getShippingTownship() {
		return shippingTownship;
	}
	public void setShippingTownship(AvailablePlace shippingTownship) {
		this.shippingTownship = shippingTownship;
	}
	public User getDeliveryMan() {
		return deliveryMan;
	}
	public void setDeliveryMan(User deliveryMan) {
		this.deliveryMan = deliveryMan;
	}
	
	
	
   
}
