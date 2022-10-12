package com.mmit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.Category;
import com.mmit.model.repo.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo repo;

	public List<Category> findAll() {
		return repo.findAll();
	}
	
	public Category save(Category cate) {
		return repo.save(cate);
	}

	public Category findById(int cateId) {
		return repo.findById(cateId).get();
	}

	public void deleteById(int cateId) {
		repo.deleteById(cateId);
	}

	
	
}
