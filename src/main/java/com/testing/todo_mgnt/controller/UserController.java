package com.testing.todo_mgnt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@GetMapping("/create")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("data", user);
		return "user-detail";
	}

	@PostMapping("/create")
	public String registration(@Valid @ModelAttribute("data") UserDto userDto, BindingResult result, Model model,
			RedirectAttributes attributes) {
		try {
			User existingUser = userService.findByUsername(userDto.getUsername());

			if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
				return "redirect:/user/create?duplicate";
			}
			userService.saveUser(userDto);
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("duplicate", "Duplicate entry not possible");
			e.printStackTrace();
			model.addAttribute("user", userDto);
			return "user-detail";
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Error server");
		}
		return "redirect:/user/list";
	}

	@GetMapping("/list")
	public String users(Model model) {
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("data_list", users);
		return "user-list";
	}

}