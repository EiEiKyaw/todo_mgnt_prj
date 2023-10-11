package com.testing.todo_mgnt.service;

import java.util.List;

import com.testing.todo_mgnt.entity.Todo;

public interface TodoService {
	
	List<Todo> getAll();

	void create(Todo todo);

	Todo findById(long id);

	void delete(Todo todo);

}
