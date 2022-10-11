package com.mmit.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class University {
	@Autowired
	private Teacher teacher;
	public University() {
		
	}
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public University(Teacher teacher) {
		this.teacher = teacher;
	}
	public void show() {
		System.out.println("University's show method");
		teacher.teach();
	}
}
