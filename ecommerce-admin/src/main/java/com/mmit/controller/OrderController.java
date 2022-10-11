package com.mmit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmit.model.entity.Category;
import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.UserService;

@Controller
public class OrderController {

	@Autowired
	private OrderService service;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/order")
	public String goOrderPage(ModelMap map) {
		List<Orders> orderList = service.findAll();
		List<Integer> totalList = new ArrayList<>();
		
		for(var o: orderList) {
			int total = 0;
			for(var i: o.getItems()) {
				total +=  i.getSub_total();
			}
			totalList.add(total);
		}
		map.put("orderList", orderList);
		map.put("priceTotal", totalList);
		return "order";
	}
	
	@GetMapping("/order/detail/{id}")
	public String goOrderdetail(ModelMap map,@PathVariable("id") long orderId) {
		Orders order = service.findById(orderId);
		int total = 0;
		for(var i: order.getItems()) {
			total += i.getSub_total();
		}
		map.put("order", order);
		map.put("total", total);
		return "order-detail";
	}
	
	@GetMapping("/order/received/{id}")
	public String orderReceived(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.received);
		service.save(receivedOrder);
		return "redirect:/order";
	}
	
	@GetMapping("/order/cancelled/{id}")
	public String orderCancelled(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.cancelled);
		service.save(receivedOrder);
		return "redirect:/order";
	}
	
	@GetMapping("/order/delivered/{id}")
	public String orderDelivered(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.delivered);
		service.save(receivedOrder);
		return "redirect:/order";
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
	}
}
