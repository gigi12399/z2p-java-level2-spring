package com.mmit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/403")
	public String deniedPage() {
		return "403";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
