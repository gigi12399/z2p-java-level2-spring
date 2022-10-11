package com.mmit.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.repo.UserRepo;

@Controller
public class UserService {
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserRepo repo;

	public List<User> findByRole(UserRole seller) {
		return repo.findByRole(seller);
	}

	public User findById(int userId) {
		return repo.findById(userId).get();
	}

	public void saveBanned(User bannedSeller) {
		repo.save(bannedSeller);
	}

	public long countUser() {
		return repo.count();
	}

	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}

	public User findUserByEmail(String username) {
		return repo.findUserByEmail(username);
	}

	public User findUserByEmailAndPhone(String email, String phone) {
		return repo.findUserByEmailAndPhone(email, phone);
	}

	public void delete(User admin) {
		repo.delete(admin);
	}

	 

	
}
