package com.mmit.storeAdmin.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.AvailablePlace;
import com.mmit.model.entity.Category;
import com.mmit.model.entity.OrderItem;
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.entity.UserStatus;
import com.mmit.model.service.AvailablePlaceService;
import com.mmit.model.service.OrderItemService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class AdminUserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private AvailablePlaceService placeService;
	
	@Autowired
	private OrderItemService itemService;
	
	@GetMapping("/admin/seller")
	public String goSellerPage(ModelMap map) {
		List<User> sellerList = service.findByRole(UserRole.SELLER);
		map.put("sellerList", sellerList);
		return "admin/seller";
	}
	
	@GetMapping("/admin/admin")
	public String goAdminPage(ModelMap map) {
		List<User> adminList = service.findByRole(UserRole.ADMIN);
		map.put("adminList", adminList);
		return "admin/admin";
	}
	
	@GetMapping("/admin/deliveryman")
	public String goDeliverymanPage(ModelMap map) {
		List<User> deliverymanList = service.findByRole(UserRole.DELIVERYMAN);
		map.put("deliverymanList", deliverymanList);
		return "admin/deliveryman";
	}
	
	@GetMapping("/admin/register")
	public String registerPage(ModelMap map) {
		List<String> cityList = placeService.findCityList();
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("placeList", placeList);
		map.put("cityList", cityList);
		map.put("user", new User());
		return "admin/register";
	}
	
	@PostMapping("/admin/user/save")
	public String saveUser(@ModelAttribute("user") User newUser,@RequestParam("uploadPhoto") MultipartFile file) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		if((newUser.getId() == 0 || newUser.getId() != 0) && !"".equals(photoName)) {
			newUser.setPhoto(photoName);
		}
		if(newUser.getStatus() == null) {
			newUser.setStatus(UserStatus.active);
		}
		User savedUser = service.save(newUser);
		
		if(!"".equals(photoName)) {
			String photoPath = "upload-photos/user/" + savedUser.getId();
			FileUploadUtil.savePhoto(photoName, photoPath, file);
		}
		return "redirect:/admin/dashboard";
	}
	
	@GetMapping("/admin/customer")
	public String goCustomerPage(ModelMap map) {
		List<User> customerList = service.findByRole(UserRole.CUSTOMER);
		map.put("customerList", customerList);
		return "admin/customer";
	}
	
	@GetMapping("/admin/seller/banned/{id}")
	public String bannedSeller(@PathVariable("id") int userId) {
		User bannedSeller = service.findById(userId);
		bannedSeller.setStatus(UserStatus.banned);
		service.saveBanned(bannedSeller);
		return "redirect:/admin/seller";
	}
	
	@GetMapping("/admin/admin/banned/{id}")
	public String bannedAdmin(@PathVariable("id") int userId) {
		User bannedAdmin = service.findById(userId);
		bannedAdmin.setStatus(UserStatus.banned);
		service.saveBanned(bannedAdmin);
		return "redirect:/admin/admin";
	}
	
	@GetMapping("/admin/customer/banned/{id}")
	public String bannedCustomer(@PathVariable("id") int userId) {
		User bannedCustomer = service.findById(userId);
		bannedCustomer.setStatus(UserStatus.banned);
		service.saveBanned(bannedCustomer);
		return "redirect:/admin/customer";
	}
	
	@GetMapping("/admin/deliveryman/banned/{id}")
	public String bannedDeliveryMan(@PathVariable("id") int userId) {
		User bannedDeliveryMan = service.findById(userId);
		bannedDeliveryMan.setStatus(UserStatus.banned);
		service.saveBanned(bannedDeliveryMan);
		return "redirect:/admin/deliveryman";
	}
	
	@GetMapping("/admin/profile")
	public String profilePage(ModelMap map) {
		List<AvailablePlace> placeList = placeService.findAll();
		map.put("placeList", placeList);
		return "admin/profile";
		
	}
	
	
	@PostMapping("/admin/profile/change")
	public String profileChange(@ModelAttribute("loginAdmin") User user) {
		service.saveUser(user);
		return "redirect:/admin/profile";
	}
	
	@PostMapping("/admin/profileImg/change")
	public String profileImgChange(@RequestParam("uploadPhoto") MultipartFile file,Principal principal) {
		User user = service.findUserByEmail(principal.getName());
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		user.setPhoto(photoName);
		service.saveUser(user);
		String photoPath = "upload-photos/user/" + user.getId();
		FileUploadUtil.savePhoto(photoName, photoPath, file);
		return "redirect:/admin/profile";
	}
	
	@GetMapping("/admin/profile/delete")
	public String profileDelete(Principal principal) {
		service.delete(service.findUserByEmail(principal.getName()));
		return "redirect:/";
	}
	
	@GetMapping("/admin/seller/detail/{id}")
	public String sellerDetail(@PathVariable("id") int sellerId,ModelMap map) {
		User seller = service.findById(sellerId);
		List<Product> allProdList = prodService.findProductListBySellerId(sellerId);
		List<OrderItem> sellerOrderList = itemService.findOrderItemListBySellerId(sellerId);
		List<Product> cutProdList = new ArrayList<>();
		if(allProdList.size() > 3) {
			for(var i = 0; i < 3; i++) {
				cutProdList.add(allProdList.get(i));
			}
		}else {
			cutProdList.addAll(allProdList);
		}
		
		map.put("sellerProductlist", cutProdList);
		map.put("seller", seller);
		map.put("orderNumber", sellerOrderList.size());
		return "admin/seller-detail";
	}
	
	
	
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = service.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
	}
}
