package com.mmit.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mmit.model.entity.User;
import com.mmit.model.repo.UserRepo;

public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Could not find this user");
		}
		MyUserDetail userDetail = new MyUserDetail(user);
		return userDetail;
	}

}
