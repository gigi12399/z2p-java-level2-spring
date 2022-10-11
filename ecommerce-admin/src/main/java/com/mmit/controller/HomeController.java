package com.mmit.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String goHome() {
		return "redirect:/dashboard";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/forgetPassword")
	public String changePasswordPage() {
		return "confirm-user";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("phone") String phone,@RequestParam("email") String email,ModelMap map) {
		User user = userService.findUserByEmailAndPhone(email,phone);
		if(user == null) {
			map.put("message", "Your credentials is not found");
			return "confirm-user";
		}
		map.put("passChangeUser", user);
		return "reset-password";
	}
	
	@PostMapping("/successPassChange")
	public String successPassChange(@RequestParam("password") String password,@ModelAttribute("passChangeUser") User pUser) {
		int pUserId = pUser.getId();
		System.out.println("pUserId----->" + pUserId);
		System.out.println("password----->" + password);
		User user = userService.findById(pUserId);
		user.setPassword(password);
		userService.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/dashboard")
	public String goDashboard(Principal principal,ModelMap map) {
		List<Orders> orderList = service.findAll();
		List<Orders> latestOrders = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
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
		User user = userService.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
		map.put("latestOrderList", latestOrders);
		map.put("priceTotal", totalList);
		return "dashboard";
	}
	
}
