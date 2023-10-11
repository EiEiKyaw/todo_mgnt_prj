package com.testing.todo_mgnt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("")
	public String getHome() {
		return "index";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin() {
		return "index";
	}

	@GetMapping("/home")
	public String getHomePage() {
		return "index";
	}

}
