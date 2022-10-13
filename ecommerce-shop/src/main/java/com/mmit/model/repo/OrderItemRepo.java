package com.mmit.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

	@Query("SELECT oi FROM OrderItem oi WHERE oi.product.seller.id = :input")
	List<OrderItem> findOrderItemListBySellerId(@Param("input") int sellerId);
}
