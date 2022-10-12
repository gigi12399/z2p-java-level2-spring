package com.mmit.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.OrderStatus;
import com.mmit.model.entity.Orders;
import com.mmit.model.entity.Product;
import com.mmit.model.entity.User;

public interface OrderRepo extends JpaRepository<Orders, Long> {
	@Query("SELECT o FROM Orders o WHERE o.customer.id = :input ORDER BY o.id DESC")
	List<Orders> findOrderByCustomerId(@Param("input") int customerId);
	
	@Query("SELECT o FROM Orders o WHERE o.deliveryMan.id = :input")
	List<Orders> findOrderByDeliveryManId(@Param("input") int deliveryManId);
	
	@Query("SELECT o FROM Orders o WHERE o.shippingTownship.city = :input")
	List<Orders> findOrderByDeliveryManCity(@Param("input") String city);
	
	@Query("SELECT o FROM Orders o WHERE o.deliveryMan.id = :input AND o.status = :status")
	List<Orders> findOrderByDeliveredStatusAndDeliId(@Param("input") int id,@Param("status") OrderStatus status);

	@Query("SELECT o FROM Orders o WHERE o.status = :status")
	List<Orders> findPendingOrders(@Param("status") OrderStatus status);
} 
