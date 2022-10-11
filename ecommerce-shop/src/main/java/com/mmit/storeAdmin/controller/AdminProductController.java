package com.mmit.storeAdmin.controller;

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
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.service.CategoryService;
import com.mmit.model.service.ProductService;
import com.mmit.model.service.UserService;

@Controller
public class AdminProductController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private CategoryService cateService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/product/add")
	public String addProduct() {
		return "admin/product-add";
	}
	
	@GetMapping("/admin/product/list")
	public String productList(ModelMap map,Principal principal) {
		User loginUser = userService.findUserByEmail(principal.getName());
		if(loginUser.getRole() != UserRole.SELLER) {
			map.put("productList", service.findAll());
			return "admin/product-list";
		}
		map.put("productList", service.findProductListBySellerId(loginUser.getId()));
		
		
		return "admin/product-list";
	}
	
	@GetMapping("/admin/product/edit/{id}")
	public String editProduct(@PathVariable("id") long prodId,ModelMap map) {
		Product prod = service.findById(prodId);
		map.put("product", prod);
		return "admin/product-add";
	}
	
	@PostMapping("/admin/product/save")
	public String saveProduct(@ModelAttribute("product") Product prod,@RequestParam("uploadPhoto") MultipartFile file,Principal principal) {
		String photoName = StringUtils.cleanPath(file.getOriginalFilename());
		User loginUser = userService.findUserByEmail(principal.getName());
		if((prod.getId() == 0 || prod.getId() != 0) && !photoName.equals("")) {
			prod.setPhoto(photoName);
		}
		
		if(prod.getSeller() == null) {
			prod.setSeller(loginUser);
		}
		Product savedProd = service.save(prod);
		
		
		if(!"".equals(photoName)) {
			String photoPath = "upload-photos/product/" + savedProd.getId();
			System.out.println("save product : " + photoPath);
			FileUploadUtil.savePhoto(photoName, photoPath, file);
		}
		return "redirect:/admin/product/list";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") long prodId) {
		service.deleteById(prodId);
		return "redirect:/admin/product/list";
	}
	
	@GetMapping("/admin/product/detail/{id}")
	public String productDetail(@PathVariable("id") long prodId,ModelMap map) {
		Product prod = service.findById(prodId);
		map.put("product", prod);
		System.out.println("product name:   " + prod.getName());
		return "admin/product-detail";
	}
	
	@GetMapping("/admin/seller/product/detail/{id}")
	public String productDetailBySeller(@PathVariable("id") int sellerId,ModelMap map) {
		List<Product> prodList = service.findProductListBySellerId(sellerId);
		User seller = userService.findById(sellerId);
		map.put("productBySeller", prodList);
		map.put("seller", seller);
		return "admin/seller-product-detail";
	}
	
	@ModelAttribute
	public void assignDefaultModel(ModelMap map,Principal principal) {
		User user = userService.findUserByEmail(principal.getName());
		map.put("categoryList", cateService.findAll());
		map.put("product", new Product());
		map.put("loginAdmin", user);
	}
}
