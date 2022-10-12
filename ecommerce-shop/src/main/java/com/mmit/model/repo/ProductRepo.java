package com.mmit.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.Category;
import com.mmit.model.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p.category.id = :input")
	List<Product> findProductByCategoryId(@Param("input") int cateId);
	
	@Query("SELECT p FROM Product p WHERE p.seller.id = :input")
	List<Product> findProductListBySellerId(@Param("input") int sellerId);
}
