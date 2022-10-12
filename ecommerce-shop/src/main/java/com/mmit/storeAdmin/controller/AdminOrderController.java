package com.mmit.storeAdmin.controller;

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
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.OrderItemService;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.UserService;

@Controller
public class AdminOrderController {

	@Autowired
	private OrderService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderItemService itemService;
	
	@GetMapping("/admin/order")
	public String goOrderPage(ModelMap map,Principal principal) {
		List<Orders> orderList = new ArrayList<>();
		User user = userService.findUserByEmail(principal.getName());
		List<Integer> totalList = new ArrayList<>();
		if(user.getRole() == UserRole.DELIVERYMAN) {
			List<Orders> OrdersByDeliCity  =  service.findOrderByDeliveryManCity(user.getAvailablePlace().getCity());
			for(var o : OrdersByDeliCity) {
				if(o.getStatus() != OrderStatus.delivered && o.getStatus() != OrderStatus.pending && o.getStatus() != OrderStatus.cancelled) {
					orderList.add(o);
				}
			}
		}
		else {
			orderList.addAll(service.findAll());
		}
		if(orderList != null) {
			for(var o: orderList) {
				int total = 0;
				for(var i: o.getItems()) {
					total +=  i.getSub_total();
				}
				totalList.add(total);
			}
		}
		map.put("orderList", orderList);
		map.put("priceTotal", totalList);
		return "admin/order";
	}
	
	@GetMapping("/admin/order/detail/{id}")
	public String goOrderdetail(ModelMap map,@PathVariable("id") long orderId) {
		Orders order = service.findById(orderId);
		int sub_total = 0;
		for(var i: order.getItems()) {
			sub_total += i.getSub_total();
		}
		int total = order.getShippingTownship().getDeliveryFees() + sub_total;
		map.put("order", order);
		map.put("sub_total", sub_total);
		map.put("total", total);
		return "admin/order-detail";
	}
	
	@GetMapping("/admin/order/received/{id}")
	public String orderReceived(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.received);
		service.save(receivedOrder);
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/order/cancelled/{id}")
	public String orderCancelled(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.cancelled);
		service.save(receivedOrder);
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/order/shipped/{id}")
	public String orderShipped(ModelMap map,@PathVariable("id") long orderId,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		Orders receivedOrder = service.findById(orderId);
		if(receivedOrder.getDeliveryMan() == null) {
			receivedOrder.setDeliveryMan(user);
		}
		receivedOrder.setStatus(OrderStatus.shipped);
		service.save(receivedOrder);
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/order/delivered/{id}")
	public String orderDelivered(ModelMap map,@PathVariable("id") long orderId) {
		Orders receivedOrder = service.findById(orderId);
		receivedOrder.setStatus(OrderStatus.delivered);
		service.save(receivedOrder);
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/seller/order")
	public String sellerOrders(Principal principal, ModelMap map) {
		User user = userService.findUserByEmail(principal.getName());
		List<OrderItem> orderItemList = itemService.findOrderItemListBySellerId(user.getId());
		map.put("sellerOrderItemList", orderItemList);
		return "admin/seller-order";
	}
	
	
	@GetMapping("/admin/deliveryman/deliveredOrder")
	public String deliveryMandeliveredOrder(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		List<Integer> totalList = new ArrayList<>();
		List<Orders> ordersByDeliveryman = service.findOrderByDeliveredStatusAndDeliId(user.getId(), OrderStatus.delivered);
		for(var o: ordersByDeliveryman) {
			int total = 0;
			for(var i: o.getItems()) {
				total +=  i.getSub_total();
			}
			totalList.add(total);
		}
		map.put("deliveredOrder", ordersByDeliveryman);
		map.put("priceTotal", totalList);
		return "/admin/deliveredOrder";
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
	}
}
