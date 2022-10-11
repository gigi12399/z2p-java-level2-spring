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

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product save(Product prod) {
		return repo.save(prod);
	}

	public Product findById(long prodId) {
		return repo.findById(prodId).get();
	}

	public void deleteById(long prodId) {
		repo.deleteById(prodId);
	}
}
