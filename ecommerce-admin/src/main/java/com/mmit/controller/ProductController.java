package com.mmit.controller;

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
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private CategoryService cateService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/product/add")
	public String addProduct() {
		return "product-add";
	}
	
	@GetMapping("/product/list")
	public String productList(ModelMap map) {
		map.put("productList", service.findAll());
		return "product-list";
	}
	
	@GetMapping("/product/edit/{id}")
	public String editProduct(@PathVariable("id") long prodId,ModelMap map) {
		Product prod = service.findById(prodId);
		map.put("product", prod);
		return "product-add";
	}
	
	@PostMapping("/product/save")
	public String saveProduct(@ModelAttribute("product") Product prod,@RequestParam("uploadPhoto") MultipartFile file) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		if((prod.getId() == 0 || prod.getId() != 0) && !photoName.equals("")) {
			prod.setPhoto(photoName);
		}
		
		Product savedProd = service.save(prod);
		
		
		if(!"".equals(photoName)) {
			String photoPath = "upload-photos/product/" + savedProd.getId();
			System.out.println("save product : " + photoPath);
			FileUploadUtil.savePhoto(photoName, photoPath, file);
		}
		return "redirect:/product/list";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") long prodId) {
		service.deleteById(prodId);
		return "redirect:/product/list";
	}
	
	@GetMapping("/product/detail/{id}")
	public String productDetail(@PathVariable("id") long prodId,ModelMap map) {
		Product prod = service.findById(prodId);
		map.put("product", prod);
		System.out.println("product name:   " + prod.getName());
		return "product-detail";
	}
	
	@ModelAttribute
	public void assignDefaultProduct(ModelMap map) {

		map.put("categoryList", cateService.findAll());
		map.put("product", new Product());
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("categoryList", cateService.findAll());
		map.put("product", new Product());
		map.put("loginAdmin", user);
	}
}
