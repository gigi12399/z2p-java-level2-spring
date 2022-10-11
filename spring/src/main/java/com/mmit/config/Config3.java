package com.mmit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mmit.di.Teacher;
import com.mmit.di.University;

@Configuration
public class Config3 {
	@Bean
	public Teacher teacherBean() {
		return new Teacher();
	}
	
	@Bean
	public University universityBean() {
		var uni = new University(teacherBean()); // di with constructor
		return uni;
	}
	
	@Bean
	public University university() { // di with setter
		var uni = new University();
		uni.setTeacher(teacherBean());
		return uni;
	}
	
	@Bean
	public University uniBean() {
		return new University();
	}
}
