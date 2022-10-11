package com.mmit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.entities.Product;
import com.mmit.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	private ProductRepo repo;

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product save(Product prod) {
		return repo.save(prod);
	}

	public Product findById(int productId) {
		return repo.findById(productId).get();
	}

	public void deleteById(int productId) {
		repo.deleteById(productId);
	}
}
