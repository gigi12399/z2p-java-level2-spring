package com.mmit.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mmit.FileUploadUtil;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.entity.UserStatus;
import com.mmit.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping("/shop/user/save")
	public String userRegister(@ModelAttribute("user") User user,@RequestParam("uploadPhoto") MultipartFile file) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		user.setPhoto(photoName);
		user.setRole(UserRole.CUSTOMER);
		user.setStatus(UserStatus.active);
		User savedUser = service.save(user);
		String photoPath = "upload-photos/user/" + savedUser.getId();
		FileUploadUtil.savePhoto(photoName, photoPath, file);
		return "redirect:/";
	}
	
	
}
