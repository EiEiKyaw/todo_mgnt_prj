package com.testing.todo_mgnt.service;

import java.util.List;

import com.testing.todo_mgnt.dto.TodoDto;
import com.testing.todo_mgnt.dto.UserDto;

public interface TodoService {

	List<TodoDto> getAll(UserDto user);

	void create(TodoDto todo);

	TodoDto findById(Long id);

	void delete(TodoDto todo);

	void update(TodoDto todo);

	void extendTargetDate(Long id, Integer total);

}
