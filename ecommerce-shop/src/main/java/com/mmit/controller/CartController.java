package com.mmit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmit.controller.request.OrderProductData;
import com.mmit.controller.request.OrderReceiverData;
import com.mmit.controller.request.OrderRequestData;
import com.mmit.controller.request.calculateTotalData;
import com.mmit.controller.request.changeTownship;
import com.mmit.model.entity.AvailablePlace;
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.AvailablePlaceService;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class CartController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AvailablePlaceService placeService;
	
	@Autowired
	private UserService userService;
	@GetMapping("/cart/detail")
	public String home(Principal principal,ModelMap map) {
		if(principal != null) {
			User loginUser = userService.profile(principal.getName());
			map.put("loginUser", loginUser);
		}
		return "cart";
	}
	
	@PostMapping("/cart/detail/total")
	public @ResponseBody String cartPriceTotal(@RequestBody calculateTotalData obj,Principal principal) {
		System.out.println(obj.getTotal());
		List<Object> fees = new ArrayList<>();
		if(principal != null) {
			User loginUser = userService.profile(principal.getName());
			int finalTotal = obj.getTotal() + loginUser.getAvailablePlace().getDeliveryFees();
			fees.add(finalTotal);
			fees.add(loginUser.getAvailablePlace().getDeliveryFees());
			return fees  + "";
		}
		fees.add(obj.getTotal());
		fees.add(0.00);
		return fees + "";
	}
	
	@GetMapping("/cart/checkout")
	public String checkoutPage(ModelMap map,Principal principal) {
		User loginUser = userService.profile(principal.getName());// getname == email
		List<String> cityList = placeService.findCityList();
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("placeList", placeList);
		map.put("cityList", cityList);
		map.put("name", loginUser.getName());
		map.put("phone", loginUser.getPhone());
		map.put("email", loginUser.getEmail());
		map.put("address", loginUser.getAddress());
		map.put("township", loginUser.getAvailablePlace());
		map.put("loginUser", loginUser);
		return "checkout";
	}
	
	@PostMapping("/cart/changeTownship")
	public @ResponseBody String changeTownshipInCart(@RequestBody changeTownship obj) {
		System.out.println(obj.getId());
		AvailablePlace place = placeService.findById(obj.getId());
		
		return place.getDeliveryFees() + "";
	}
	
	@PostMapping("/cart/place-order")
	public @ResponseBody String makeOrder(@RequestBody OrderRequestData obj, Principal principal) {
		System.out.println("items: " + obj.getOrderItems());
		System.out.println("receiver: " + obj.getReceiver());
		try {
			OrderReceiverData receiver = obj.getReceiver();
			List<OrderProductData> itemList = obj.getOrderItems();
			AvailablePlace shippingTownship = placeService.findById(receiver.getTownship());
			// create new order
			Orders new_order = new Orders();
			new_order.setShippingAddress(receiver.getAddress());
			new_order.setShippingEmail(receiver.getEmail());
			new_order.setShippingName(receiver.getName());
			new_order.setShippingPhone(receiver.getPhone());
			new_order.setShippingTownship(shippingTownship);
			new_order.setCustomer(userService.profile(principal.getName()));
			new_order.setStatus(OrderStatus.pending);
			// add order items
			for(var item: itemList) {
				var product = productService.findById(item.getProductId());
				
				OrderItem order_item = new OrderItem();
				order_item.setProduct(product);
				order_item.setQuantity(item.getQty());
				order_item.setSub_total(item.getSub_total());
				new_order.addOrderItem(order_item);
			}
			// save order to db
			Orders savedOrder = orderService.save(new_order);
			return savedOrder.getId() + "";
		} catch (Exception e) {
			return "";
		}
	}
	
	
}
