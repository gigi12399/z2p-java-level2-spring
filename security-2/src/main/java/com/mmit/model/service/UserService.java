package com.mmit.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmit.model.entity.User;
import com.mmit.model.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo repo;
	@Autowired
	public PasswordEncoder encoder;

	public long countUser() {
		return repo.count();
	}

	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}
}
