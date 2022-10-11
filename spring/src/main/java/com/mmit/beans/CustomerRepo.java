package com.mmit.beans;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepo implements MyRepository<Customer> {

	private Map<Integer, Customer> repo;
	public CustomerRepo() {
		repo = new HashMap<>();
	}
	@Override
	public void save(Customer t) {
		repo.put(t.getId(), t);
	}

	@Override
	public Customer findCustomerById(int id) {
		
		return repo.get(id);
	}

}
