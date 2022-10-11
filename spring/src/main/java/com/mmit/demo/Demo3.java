package com.mmit.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mmit.config.Config3;
import com.mmit.di.University;

public class Demo3 {
	public static void main(String[] args) {
		ApplicationContext cxt = new AnnotationConfigApplicationContext(Config3.class);
		
		//var university = cxt.getBean("universityBean", University.class);
		//var university = cxt.getBean("university",University.class);
		var university = cxt.getBean("uniBean", University.class);
		university.show();
	}
}
