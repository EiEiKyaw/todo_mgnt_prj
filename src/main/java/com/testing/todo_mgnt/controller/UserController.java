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
import com.testing.todo_mgnt.entity.User;
import com.testing.todo_mgnt.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String registerUser(Model model) {
		model.addAttribute("data", new UserDto());
		return "user-register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("data") UserDto userDto, BindingResult result, Model model,
			RedirectAttributes attributes) {
		User existingUser = userService.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
		if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
			return "redirect:/user/register?duplicate";
		}
		userService.create(userDto);
		return "redirect:/login";
	}

	@GetMapping("/create")
	public String createUser(Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		model.addAttribute("data", new UserDto());
		return "user-detail";
	}

	@PostMapping("/create")
	public String createUser(@Valid @ModelAttribute("data") UserDto userDto, BindingResult result, Model model,
			RedirectAttributes attributes) {
		User existingUser = userService.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
		if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
			return "redirect:/user/register?duplicate";
		}
		userService.create(userDto);
		return "redirect:/user/list";
	}

	@GetMapping("/list")
	public String getUserList(Model model) {
		UserDto loginUser = userService.getLoginUser();
		model.addAttribute("login_user", loginUser);
		model.addAttribute("data_list", userService.getAll(loginUser));
		return "user-list";
	}

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
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
		User existingUser = userService.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
		if (existingUser != null && existingUser.getId() != id) {
			return "redirect:/user/edit/" + existingUser.getId() + "?duplicate";
		}
		userService.update(userDto);
		return "redirect:/user/list";
	}

}