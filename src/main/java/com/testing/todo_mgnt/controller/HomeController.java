package com.testing.todo_mgnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testing.todo_mgnt.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("")
	public String getHome() {
		return "login";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		return "index";
	}

	@GetMapping("/home")
	public String getHomePage(Model model) {
		System.out.println(userService.getLoginUser().getId());
		model.addAttribute("login_user", userService.getLoginUser());
		return "index";
	}

}
