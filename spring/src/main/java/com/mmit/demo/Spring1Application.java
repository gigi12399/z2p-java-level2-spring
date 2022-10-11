package com.mmit.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mmit.beans.Customer;
import com.mmit.beans.Person;
import com.mmit.beans.University;
import com.mmit.config.BeanConfig;

//@SpringBootApplication
public class Spring1Application {

	public static void main(String[] args) {
		//SpringApplication.run(Spring1Application.class, args);
		var cxt = new AnnotationConfigApplicationContext(BeanConfig.class);
		
		//University uni = cxt.getBean(University.class);/
		//University uni = cxt.getBean("universityBean",University.class);
		//uni.show();
		
		//var customer = cxt.getBean(Customer.class);
		//customer.save();
		//cxt.close();
		
		var person1 = cxt.getBean(Person.class);
		person1.setName("Jeon");
		
		var person2 = cxt.getBean(Person.class);
		
		System.out.println("Person1's name: " + person1.getName());
		System.out.println("Person2's name: " + person2.getName());
		
		person2.setName("Cherry");
		
		System.out.println("------------------------------------");
		System.out.println("Person1's name: " + person1.getName());
		System.out.println("Person2's name: " + person2.getName());
	}

}
