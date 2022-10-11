package com.mmit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mmit.FileUploadUtil;
import com.mmit.entities.Product;
import com.mmit.service.CategoryService;
import com.mmit.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private CategoryService catService;
	
	@GetMapping("/products")
	public String home(Model m) {
		List<Product> list = service.findAll();
		m.addAttribute("productList", list);
		return "product-list";
	}
	
	@GetMapping("/products/add")
	public String goAddPage(Model model) {
		model.addAttribute("categories", catService.findAll());
		return "product-add";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(@ModelAttribute("product") Product prod, @RequestParam("upload_file") MultipartFile file) {
		String fileName = StringUtils .cleanPath(file.getOriginalFilename()); // admin.png
		if((prod.getId() == 0 || prod.getId() != 0) && !fileName.equals("")) {
			prod.setPhoto(fileName);
		}
		
		// save to db
		var saveProduct = service.save(prod);
		// save photo
		if(!"".equals(fileName)) {
			String uploadDir = "upload-photos/" + saveProduct.getId(); //upload-photos/1
			FileUploadUtil.savePhoto(uploadDir, fileName, file);
		}
		
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable("id") int productId, Model model) {
		var prod = service.findById(productId);
		model.addAttribute("product", prod);
		model.addAttribute("categories", catService.findAll());
		return "product-add";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id") int productId) {
		service.deleteById(productId);
		return "redirect:/products";
	}
	
	@ModelAttribute
	public void assignDefaultModel(Model model) {
		model.addAttribute("page", "product");
		model.addAttribute("product", new Product());
	}
}
