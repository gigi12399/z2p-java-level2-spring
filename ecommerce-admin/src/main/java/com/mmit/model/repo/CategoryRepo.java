package com.mmit.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmit.model.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
