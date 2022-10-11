package com.mmit.model.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmit.model.entities.Employee;
import com.mmit.model.repo.EmployeeRepository;



@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	public void save(Employee emp) {
		repo.save(emp);
	}

	public List<Employee> findAll() {
		
		return repo.findAll();
	}

	public Employee findById(int id) {
		return repo.findById(id).get();
	}

	public void deleteById(int empId) {
		repo.deleteById(empId);
	}
}
