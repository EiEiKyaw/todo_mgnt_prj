package com.testing.todo_mgnt.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testing.todo_mgnt.entity.Todo;
import com.testing.todo_mgnt.service.TodoService;

@Controller
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping("/create")
	public String createTodo(Model model) {
		Todo data = new Todo();
		model.addAttribute("data", data);
		return "todo-detail";
	}

	@PostMapping("/list")
	public String createTodo(@ModelAttribute("data") Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/todo/create";
		}
		todoService.create(todo);
		return "redirect:/todo/list";
	}

	@GetMapping("/list")
	public String getTodoList(Model model) {
		model.addAttribute("standardDate", new Date());
		model.addAttribute("data_list", todoService.getAll());
		return "todo-list";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Todo todo = todoService.findById(id);
		model.addAttribute("data", todo);
		return "todo-edit";
	}

	@PostMapping("/edit/{id}")
	public String updateUser(@PathVariable("id") long id, @ModelAttribute("data") Todo todo, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			todo.setId(id);
			return "todo-edit";
		}

		todoService.create(todo);
		return "redirect:/todo/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Todo todo = todoService.findById(id);
		todoService.delete(todo);
		return "redirect:/todo/list";
	}

}
