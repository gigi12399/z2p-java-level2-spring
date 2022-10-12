package com.mmit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mmit.model.entity.Category;
import com.mmit.model.entity.Product;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService Service;
	
	@Autowired
	private CategoryService cateService;
	
	@GetMapping("/products/detail")
	public String detailPage() {
		return "product-detail";
	}
	
	@GetMapping("/search/withCategory/{id}")
	public String searchProductByCategory(@PathVariable("id") int cateId,ModelMap map) {
		List<Product> prodList = Service.findByCateId(cateId);
		map.put("categoryList", cateService.findAll());
		map.put("productList", prodList);
		return "index";
	}
}
