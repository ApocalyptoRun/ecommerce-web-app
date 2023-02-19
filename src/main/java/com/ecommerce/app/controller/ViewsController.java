package com.ecommerce.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewsController {
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin";
	}
	
	@RequestMapping("/home")
	public String home(){
		return "home";
	}

	
	@RequestMapping("/signup")
	public String footer(){
		return "signup";
	}
	
	@GetMapping("/test/{id}")
	public String test(@PathVariable long id, Model model) {
		model.addAttribute("id", id);
		return "test";
	}
}
