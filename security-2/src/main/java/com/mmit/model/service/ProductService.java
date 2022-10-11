package com.mmit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.Product;
import com.mmit.model.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	public void save(Product product) {
		repo.save(product);
	}

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product findById(int pId) {
		return repo.findById(pId).orElse(new Product());
	}

	public void deleteById(int pId) {
		repo.deleteById(pId);
	}
}
