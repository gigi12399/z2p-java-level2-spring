package com.mmit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mmit.model.entity.Product;
import com.mmit.model.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@GetMapping("/")
	public String home(ModelMap map) {
		map.put("productList", service.findAll());
		return "index";
	}
	
	@GetMapping("/product/add")
	public String addPage() {
		return "product-add";
	}
	
	@PostMapping("/product/save")
	public String saveProduct(@ModelAttribute Product product) {
		
		service.save(product);
		return "redirect:/";
	}
	
	@GetMapping("/product/edit/{id}")
	public String editProduct(@PathVariable("id") int pId,ModelMap map) {
		map.put("product", service.findById(pId));
		return "product-add";
	}
	
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") int pId) {
		service.deleteById(pId);
		return "redirect:/";
	}
	
	
	@ModelAttribute
	public void initModel(ModelMap map) {
		map.put("product", new Product());
	}
}
