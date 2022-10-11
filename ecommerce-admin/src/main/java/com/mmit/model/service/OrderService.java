package com.mmit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.repo.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo repo;

	public List<Orders> findAll() {
		return repo.findAll();
	}

	public Orders findById(long orderId) {
		return repo.findById(orderId).get();
	}

	public void save(Orders receivedOrder) {
		repo.save(receivedOrder);
	}

	
}
