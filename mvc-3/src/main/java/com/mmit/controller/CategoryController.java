package com.mmit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmit.entities.Category;
import com.mmit.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String home(Model m) {
		m.addAttribute("categories", service.findAll());
		return "category-list";
	}
	
	@GetMapping("/categories/add")
	public String goAddPage() {
		return "category-add";
	}
	
	@GetMapping("/categories/save")
	public String saveCategory(@ModelAttribute("category") Category cate) {
		service.save(cate);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable("id") int cateId,Model model) {
		model.addAttribute("category", service.findById(cateId));
		return "category-add";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable("id") int cateId) {
		service.deleteById(cateId);
		return "redirect:/categories";
	}
	
	@ModelAttribute
	public void assignDefaultModel(Model model) {
		model.addAttribute("page", "category");
		model.addAttribute("category", new Category());
	}
}
