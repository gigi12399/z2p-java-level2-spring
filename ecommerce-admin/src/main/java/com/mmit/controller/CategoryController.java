package com.mmit.controller;

import java.io.IOException;
import java.security.Principal;

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
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.UserService;
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/category")
	public String goCategory(ModelMap map) {
		map.put("CategoryList", service.findAll());
		return "category";
	}
	
	@GetMapping("/category/add")
	public String addCategory() {
		return "category-add";
	}
	
	@PostMapping("/category/save")
	public String saveCategory(@ModelAttribute("Category") Category cate, @RequestParam("uploadPhoto") MultipartFile file) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		
		if((cate.getId() == 0 || cate.getId() != 0) && !photoName.equals("")) {
			cate.setPhoto(photoName);
		}
		
		var savedCate = service.save(cate);
		
		if(!"".equals(photoName)) {
			String photoPath = "upload-photos/category/" + savedCate.getId();
			FileUploadUtil.savePhoto(photoName,photoPath,file);
		}
		
		return "redirect:/category";
	}
	
	
	@GetMapping("/category/edit/{id}")
	public String editCategory(@PathVariable("id") int cateId,ModelMap map) {
		Category cate = service.findById(cateId);
		map.put("Category", cate);
		return "category-add";
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("loginAdmin", user);
		map.put("Category", new Category());
	}
	
	
}
