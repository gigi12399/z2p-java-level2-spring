package com.mmit.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email = :input")
	User findUserByEmail(@Param("input") String email);
	
	@Query("SELECT u FROM User u WHERE u.role = :input")
	List<User> findByRole(@Param("input") UserRole seller);
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.phone = :phone")
	User findUserByEmailAndPhone(@Param("email") String email,@Param("phone") String phone);
}
