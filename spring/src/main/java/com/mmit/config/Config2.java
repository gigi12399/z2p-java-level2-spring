package com.mmit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mmit.beans.Teacher;

@Configuration
public class Config2 {

	@Bean
	public Teacher teacherBean() {
		return new Teacher();
	}
}
