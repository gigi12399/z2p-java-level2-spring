package com.mmit.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mmit.beans.Teacher;
import com.mmit.config.Config2;

public class Demo2 {
	public static void main(String[] args) {
		ApplicationContext cxt = new AnnotationConfigApplicationContext(Config2.class);
		var teacher = cxt.getBean("teacherBean",Teacher.class);
		teacher.teach();
	}
}
