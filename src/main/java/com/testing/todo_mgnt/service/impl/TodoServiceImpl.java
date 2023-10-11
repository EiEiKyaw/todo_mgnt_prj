package com.testing.todo_mgnt.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testing.todo_mgnt.entity.Todo;
import com.testing.todo_mgnt.repository.TodoRepository;
import com.testing.todo_mgnt.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepo;

	@Override
	public List<Todo> getAll() {
		return todoRepo.findAll();
	}

	@Override
	public void create(Todo todo) {
		todoRepo.save(todo);
	}

	@Override
	public Todo findById(long id) {
		Optional<Todo> entity = todoRepo.findById(id);
		if (!entity.isPresent()) {
			throw new IllegalArgumentException("Invalid user Id:" + id);
		}
		return entity.get();
	}

	@Override
	public void delete(Todo todo) {
		todoRepo.delete(todo);
	}

}
