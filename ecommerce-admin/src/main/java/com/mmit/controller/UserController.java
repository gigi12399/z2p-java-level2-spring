package com.mmit.controller;

import java.security.Principal;
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
import com.mmit.model.entity.Category;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.entity.UserStatus;
import com.mmit.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/seller")
	public String goSellerPage(ModelMap map) {
		List<User> sellerList = service.findByRole(UserRole.seller);
		map.put("sellerList", sellerList);
		return "seller";
	}
	
	@GetMapping("/register")
	public String registerPage(ModelMap map) {
		map.put("user", new User());
		return "register";
	}
	
	@PostMapping("/user/save")
	public String saveUser(@ModelAttribute("user") User newUser,@RequestParam("uploadPhoto") MultipartFile file) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		if((newUser.getId() == 0 || newUser.getId() != 0) && !"".equals(photoName)) {
			newUser.setPhoto(photoName);
			newUser.setStatus(UserStatus.active);
		}
		
		User savedUser = service.save(newUser);
		
		if(!"".equals(photoName)) {
			String photoPath = "upload-photos/user/" + savedUser.getId();
			FileUploadUtil.savePhoto(photoName, photoPath, file);
		}
		return "redirect:/dashboard";
	}
	
	@GetMapping("/customer")
	public String goCustomerPage(ModelMap map) {
		List<User> customerList = service.findByRole(UserRole.customer);
		map.put("customerList", customerList);
		return "customer";
	}
	
	@GetMapping("/seller/banned/{id}")
	public String bannedSeller(@PathVariable("id") int userId) {
		User bannedSeller = service.findById(userId);
		bannedSeller.setStatus(UserStatus.banned);
		service.saveBanned(bannedSeller);
		return "redirect:/seller";
	}
	
	@GetMapping("/customer/banned/{id}")
	public String bannedCustomer(@PathVariable("id") int userId) {
		User bannedCustomer = service.findById(userId);
		bannedCustomer.setStatus(UserStatus.banned);
		service.saveBanned(bannedCustomer);
		return "redirect:/customer";
	}
	
	@GetMapping("/profile")
	public String profilePage() {
		return "profile";
	}
	
	
	@PostMapping("/profile/change")
	public String profileChange(@ModelAttribute("loginAdmin") User user) {
		service.save(user);
		return "redirect:/profile";
	}
	
	@PostMapping("/profileImg/change")
	public String profileImgChange(@RequestParam("uploadPhoto") MultipartFile file,Principal principal) {
		User user = service.findUserByEmail(principal.getName());
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		user.setPhoto(photoName);
		service.save(user);
		String photoPath = "upload-photos/user/" + user.getId();
		FileUploadUtil.savePhoto(photoName, photoPath, file);
		return "redirect:/profile";
	}
	
	@GetMapping("/profile/delete")
	public String profileDelete(Principal principal) {
		service.delete(service.findUserByEmail(principal.getName()));
		return "redirect:/login";
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = service.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
	}
}
