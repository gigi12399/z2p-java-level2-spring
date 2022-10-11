package com.mmit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmit.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

}
