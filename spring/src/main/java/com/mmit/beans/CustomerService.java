package com.mmit.beans;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	public Customer findById(int id) {
		if(id <= 0)
			return null;
		else {
			var cust = new Customer();
			cust.setId(id);
			cust.setName("Jeon");
			return cust;
		}
	}
}
