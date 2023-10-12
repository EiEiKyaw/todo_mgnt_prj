package com.testing.todo_mgnt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/create")
	public String createUser(Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		model.addAttribute("data", new UserDto());
		return "user-detail";
	}

	@PostMapping("/create")
	public String createUser(@Valid @ModelAttribute("data") UserDto userDto, BindingResult result, Model model,
			RedirectAttributes attributes) {
		UserDto existingUser = userService.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
		if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
			return "redirect:/user/create?duplicate";
		}
		userService.create(userDto);
		return "redirect:/login";
	}

	@GetMapping("/list")
	public String getUserList(Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		model.addAttribute("data_list", userService.getAll());
		return "user-list";
	}

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		UserDto user = userService.findById(id);
		System.out.println(user.getPassword());
		model.addAttribute("data", userService.findById(id));
		return "user-edit";
	}

	@PostMapping("/edit/{id}")
	public String editUser(@PathVariable("id") long id, @ModelAttribute("data") UserDto userDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			userDto.setId(id);
			return "user-edit";
		}
		UserDto existingUser = userService.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
		System.out.println(".." + existingUser != null);
		System.out.println("...." + (existingUser.getId() == id));
		if (existingUser != null && existingUser.getId() != id) {
			System.out.println("............................");
			return "redirect:/user/edit/" + existingUser.getId() + "?duplicate";
		}
		userService.update(userDto);
		return "redirect:/user/list";
	}

}