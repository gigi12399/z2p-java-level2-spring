package com.mmit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mmit.model.entity.User;
import com.mmit.model.service.UserService;

public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService service;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = service.findUserByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Authentication Fail");
		}
		MyUserDetails userDetail = new MyUserDetails(user);
		return userDetail;
	}

}
