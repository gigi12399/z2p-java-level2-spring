package com.mmit.model.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.User;
import com.mmit.model.entity.UserRole;
import com.mmit.model.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;

	@Autowired
	private PasswordEncoder encoder;
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public void saveUser(User user) {
		repo.save(user);
	}

	public long countUser() {
		return repo.count();
	}

	public User profile(String email) {
		// TODO Auto-generated method stub
		return repo.findUserByEmail(email);
	}
	
	public List<User> findByRole(UserRole seller) {
		return repo.findByRole(seller);
	}

	public User findById(int userId) {
		return repo.findById(userId).get();
	}

	public void saveBanned(User bannedSeller) {
		repo.save(bannedSeller);
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
