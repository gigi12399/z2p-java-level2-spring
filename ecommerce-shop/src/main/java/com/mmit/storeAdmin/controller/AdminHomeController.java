package com.mmit.storeAdmin.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmit.model.entity.AvailablePlace;
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;
import com.mmit.model.service.AvailablePlaceService;
import com.mmit.model.service.OrderItemService;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class AdminHomeController {
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private AvailablePlaceService placeService;

	@GetMapping("/admin")
	public String goHome() {
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String goDashboard(Principal principal,ModelMap map) {
		List<Orders> orderList = service.findAll();
		List<OrderItem> orderItemList = orderItemService.findAll();
		List<Product> productList = prodService.findAll();
		User user = userService.findUserByEmail(principal.getName());
		List<Orders> latestOrders = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		List<Orders> pendingOrderList = service.findPendingOrders(OrderStatus.pending);
		
		// admin and deliveryman
		if(orderList.size() <= 10) {
			for(var i=1; i <= orderList.size(); i++) {
				latestOrders.add(orderList.get(orderList.size()-i));
			}
		}
		else {
			for(var i=1; i<=10; i++) {
				latestOrders.add(orderList.get(orderList.size()-i));
			}
		}
		
		
		for(var o: latestOrders) {
			int total = 0;
			for(var i: o.getItems()) {
				total +=  i.getSub_total();
			}
			totalList.add(total);
		}
		
		int allTotal = 0;
		for(var item: orderItemList) {
			allTotal += item.getSub_total();
		}
		
		// seller
	
		  int sellerTotalOrders = 0;
		  int sellerTotalEarning = 0;
		  for(var o: orderItemList) {
			  if(o.getProduct().getSeller().getId() == user.getId()) {
				  sellerTotalOrders += 1;
				  sellerTotalEarning += o.getSub_total();
			  } 
		  }
		  
		  List<Product> sellerProductList =
		  prodService.findProductListBySellerId(user.getId());
		 
		
		
		map.put("loginAdmin", user);
		map.put("latestOrderList", latestOrders);
		map.put("priceTotal", totalList);
		map.put("pendingOrders", pendingOrderList.size());
		map.put("Totalprod", productList.size());
		map.put("TotalOrders", orderList.size());
		map.put("totalEarning", allTotal);
		// seller 
		
		 map.put("sellerTotalorders",sellerTotalOrders); map.put("sellerTotalEarning",
		 sellerTotalEarning); map.put("sellerProduct", sellerProductList.size());
		 
		return "admin/dashboard";
	}
	
	@GetMapping("/admin/availablePlace")
	public String goPlacePage(ModelMap map) {
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("availablePlaceList", placeList);
		return "admin/availablePlace";
	}
	
	@GetMapping("/admin/availablePlace/add")
	public String goAddPlacePage(ModelMap map) {
		map.put("availablePlace", new AvailablePlace());
		return "admin/availablePlace-add";
	}
	
	@PostMapping("/admin/availablePlace/save")
	public String saveAvailablePlace(@ModelAttribute("availablePlace") AvailablePlace place) {
		placeService.save(place);
		return "redirect:/admin/availablePlace";
	}
	
	@GetMapping("/admin/availablePlace/edit/{id}")
	public String editAvailablePlace(@PathVariable("id") int placeId,ModelMap map) {
		AvailablePlace place = placeService.findById(placeId);
		map.put("availablePlace", place);
		return "admin/availablePlace-add";
	}
	
	@ModelAttribute
	public void assignDefaultLoginUser(Principal principal,ModelMap map) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
	}
	
}
