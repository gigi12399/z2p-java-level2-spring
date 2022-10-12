package com.mmit.model.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.repo.OrderRepo;

@Service
public class OrderService {
	@Autowired
	private OrderRepo repo;

	public Orders save(Orders new_order) {
		return repo.save(new_order);
	}
	
	public List<Orders> findAll() {
		return repo.findAll();
	}

	public Orders findById(long orderId) {
		return repo.findById(orderId).get();
	}/*
		 * 
		 * public void save(Orders receivedOrder) { repo.save(receivedOrder); }
		 */

	public List<Orders> findOrderByCustomerId(int id) {
		return repo.findOrderByCustomerId(id);
	}

	public List<Orders> findOrderByDeliveryManCity(String city) {
		return repo.findOrderByDeliveryManCity(city);
	}

	public List<Orders> findOrderByDeliveredStatusAndDeliId(int id,OrderStatus delivered) {
		return repo.findOrderByDeliveredStatusAndDeliId(id,delivered);
	}

	public List<Orders> findOrderByDeliveryManId(int id) {
		return repo.findOrderByDeliveryManId(id);
	}

	public List<Orders> findPendingOrders(OrderStatus pending) {
		return repo.findPendingOrders(pending);
	}

	

	
}
