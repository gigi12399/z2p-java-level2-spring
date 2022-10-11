package com.mmit.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class Customer {
	private int id;
	private String name;
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Customer() {
		
	}

	@PostConstruct
	public void start() {
		System.out.println("This is init method......");
	}
	
	@PreDestroy
	public void end() {
		System.out.println("This is destroy method.....");
	}
	
	public void save() {
		System.out.println("This is utility method.....");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
	
}
