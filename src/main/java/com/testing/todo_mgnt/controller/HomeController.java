package com.testing.todo_mgnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.service.UserService;
import com.testing.todo_mgnt.util.Status;

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
		UserDto loginUser = userService.getLoginUser();
		model.addAttribute("login_user", loginUser);
		if (loginUser.getStatus().equals(Status.INACTIVE)) {
			return "disable-user";
		}
		return "index";
	}

}
