package com.testing.todo_mgnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testing.todo_mgnt.dto.TodoDto;
import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.entity.Todo;
import com.testing.todo_mgnt.service.TodoService;
import com.testing.todo_mgnt.service.UserService;

@Controller
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private UserService userService;

	@GetMapping("/create")
	public String createTodo(Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		model.addAttribute("data", new Todo());
		return "todo-detail";
	}

	@PostMapping("/list")
	public String createTodo(@ModelAttribute("data") TodoDto todo, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "redirect:/todo/create";
			}
			todo.setCreatedUser(userService.getLoginUser().getId());
			todoService.create(todo);
			return "redirect:/todo/list";
		} catch (Exception e) {
			return "redirect:/todo/create?error";
		}
	}

	@GetMapping("/list")
	public String getTodoList(Model model) {
		UserDto loginUser = userService.getLoginUser();
		model.addAttribute("login_user", loginUser);
		model.addAttribute("data_list", todoService.getAll(loginUser));
		return "todo-list";
	}

	@GetMapping("/edit/{id}")
	public String editTodo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("login_user", userService.getLoginUser());
		model.addAttribute("data", todoService.findById(id));
		return "todo-edit";
	}

	@PostMapping("/edit/{id}")
	public String editTodo(@PathVariable("id") Long id, @ModelAttribute("data") TodoDto todo, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			todo.setId(id);
			return "todo-edit";
		}

		todoService.update(todo);
		return "redirect:/todo/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteTodo(@PathVariable("id") Long id, Model model) {
		TodoDto todo = todoService.findById(id);
		todoService.delete(todo);
		return "redirect:/todo/list";
	}

	@GetMapping("/extend/3/{id}")
	public String extendThreeTargetDate(@PathVariable("id") Long id, Model model) {
//		model.addAttribute("login_user", userService.getLoginUser());
//		model.addAttribute("data", todoService.findById(id));
		todoService.extendTargetDate(id, 3);
		return "redirect:/todo/list";
	}

	@GetMapping("/extend/5/{id}")
	public String extendFiveTargetDate(@PathVariable("id") long id, Model model) {
//		model.addAttribute("login_user", userService.getLoginUser());
//		model.addAttribute("data", todoService.findById(id));
		todoService.extendTargetDate(id, 5);
		return "redirect:/todo/list";
	}

}
