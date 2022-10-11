package com.mmit.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	//@RequestMapping(method = RequestMethod.GET, name = "/home")
	@GetMapping("/home")
	public String goHome() {
		return "return-type"; // logical name (template/return-type.html)
	}
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") int uid) {
		System.out.println("userId: " + uid);
		return "void";
	}
	@GetMapping("/string")
	public String goString(Model m, @RequestParam(name = "name", required = false) String uname,
			@RequestParam(name = "age", required = false) int age) {
		m.addAttribute("time", LocalTime.now());
		m.addAttribute("username", uname);
		m.addAttribute("userage", age);
		return "string-page";
	}
	@GetMapping("/void") // view-name => void.html
	public void goVoid() {
		
	}
	@GetMapping("/mv")
	public ModelAndView goModelAndView() {
		ModelMap m = new ModelMap();
		m.addAttribute("date", LocalDate.now());
		m.put("msg", "This is <b>Spring MVC</b>!");
		ModelAndView obj = new ModelAndView("view-with-model", m);
		return obj;
	}
}
