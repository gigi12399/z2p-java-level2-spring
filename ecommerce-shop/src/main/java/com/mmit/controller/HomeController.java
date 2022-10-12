package com.mmit.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.AvailablePlace;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.User;
import com.mmit.model.service.AvailablePlaceService;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.OrderService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService service;
	@Autowired
	private CategoryService catService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AvailablePlaceService placeService;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/shop";
	}
	
	@GetMapping("/shop")
	public String goShop(ModelMap map) {
		map.put("productList", service.findAll());
		map.put("categoryList", catService.findAll());
		return "index";
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
	public String successPassChange(@RequestParam("password") String password,@ModelAttribute("passChangeUser") User user) {
		user.setPassword(password);
		userService.save(user);
		return "redirect:/login";
	}

	@GetMapping("/shop/register")
	public String registerPage(ModelMap map) {
		List<String> cityList = placeService.findCityList();
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("placeList", placeList);
		map.put("cityList", cityList);
		map.put("user", new User());
		return "register";
	}
	
	@GetMapping("/shop/about")
	public String aboutPage() {
		return "about";
	}
	
	@GetMapping("/shop/contact")
	public String contactPage() {
		return "contact";
	}
	
	@GetMapping("/shop/orders")
	public String myOrderPage(Principal principal,ModelMap map) {
		User user = userService.findUserByEmail(principal.getName());
		List<Orders> orderList = orderService.findOrderByCustomerId(user.getId());
		List<Integer> qtyList = new ArrayList<>();
		List<Integer> totalList = new ArrayList<>();
		List<LocalDateTime> deliveredDate = new ArrayList<>();
		for(var order: orderList) {
			int qty = 0;
			int total = 0;
			LocalDateTime date = order.getCreated_at().plusDays(3);
			for(var item: order.getItems()) {
				qty += item.getQuantity();
				total += item.getSub_total();
			}
			qtyList.add(qty);
			totalList.add(total);
			deliveredDate.add(date);
		}
		 
		map.put("orderList", orderList);
		map.put("qtyList", qtyList);
		map.put("totalList", totalList);
		map.put("deliveredDate", deliveredDate);
		return "myorder";
	}
	
	@GetMapping("/shop/profile")
	public String myProfilePage(Principal principal,ModelMap map) {
		User user = userService.findUserByEmail(principal.getName());
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("placeList", placeList);
		map.put("loginUser", user);
		return "myProfile";
	}
	
	@PostMapping("/shop/profile/change")
	public String profileChange(@ModelAttribute("loginUser") User user) {
		userService.saveUser(user);
		return "redirect:/shop/profile";
	}
	
	@PostMapping("/shop/profileImg/change")
	public String profileImgChange(@RequestParam("uploadPhoto") MultipartFile file,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		user.setPhoto(photoName);
		userService.saveUser(user);
		String photoPath = "upload-photos/user/" + user.getId();
		FileUploadUtil.savePhoto(photoName, photoPath, file);
		return "redirect:/shop/profile";
	}
	
	@GetMapping("/shop/profile/delete")
	public String profileDelete(Principal principal) {
		userService.delete(userService.findUserByEmail(principal.getName()));
		return "redirect:/";
	}
	
	@GetMapping("/shop/products/{id}")
	public String singleProduct(@PathVariable("id") long productId,ModelMap map) {
		map.put("product", service.findById(productId));
		return "product-detail";
	}
}
