package com.mmit.model.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.OrderItem;
import com.mmit.model.repo.OrderItemRepo;

@Service
public class OrderItemService implements Serializable{

	@Autowired
	private OrderItemRepo repo;

	public List<OrderItem> findOrderItemListBySellerId(int id) {
		return repo.findOrderItemListBySellerId(id);
	}

	public List<OrderItem> findAll() {
		return repo.findAll();
	}

	
}
