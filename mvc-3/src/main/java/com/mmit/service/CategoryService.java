package com.mmit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.entities.Category;
import com.mmit.repo.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepo repo;

	public List<Category> findAll() {
		return repo.findAll();
	}

	public void deleteById(int cateId) {
		repo.deleteById(cateId);
	}

	public void save(Category cate) {
		repo.save(cate);
	}

	public Category findById(int cateId) {
		return repo.findById(cateId).get();
	}
}
