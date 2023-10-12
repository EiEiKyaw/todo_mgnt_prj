package com.testing.todo_mgnt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testing.todo_mgnt.dto.TodoDto;
import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.entity.Todo;
import com.testing.todo_mgnt.repository.TodoRepository;
import com.testing.todo_mgnt.repository.UserRepository;
import com.testing.todo_mgnt.service.TodoService;
import com.testing.todo_mgnt.util.TodoStatus;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<TodoDto> getAll(UserDto user) {
		List<Todo> todos = new ArrayList<>();
		if (user.getRole().contains("Admin")) {
			todos = todoRepo.findAll();
		} else {
			todos = todoRepo.findByCreatedUser(user.getId());
		}
		return todos.stream().map((todo) -> mapToTodoDto(todo)).collect(Collectors.toList());
	}

	@Override
	public void create(TodoDto todo) {
		todoRepo.save(mapToTodo(todo));
	}

	@Override
	public TodoDto findById(Long id) {
		Optional<Todo> entity = todoRepo.findById(id);
		if (!entity.isPresent()) {
			throw new IllegalArgumentException("Invalid todo id:" + id);
		}
		return mapToTodoDto(entity.get());
	}

	@Override
	public void delete(TodoDto todo) {
		todoRepo.deleteById(todo.getId());
	}

	@Override
	public void update(TodoDto dto) {
		Todo oldTodo = todoRepo.findById(dto.getId()).get();
		Todo newTodo = mapToTodo(dto);
		newTodo.setTargetedDate(oldTodo.getTargetedDate());
		newTodo.setCreatedUser(oldTodo.getCreatedUser());
		todoRepo.save(newTodo);
	}

	private TodoDto mapToTodoDto(Todo todo) {
		TodoDto todoDto = new TodoDto();
		if (todo.getId() != null) {
			todoDto.setId(todo.getId());
		}
		todoDto.setTitle(todo.getTitle());
		todoDto.setDescription(todo.getDescription());
		todoDto.setDetail(todo.getDetail());
		todoDto.setStatus(todo.getStatus());
		todoDto.setStatusText(todo.getStatus().equals(TodoStatus.TODO) ? "Todo"
				: (todo.getStatus().equals(TodoStatus.IN_PROGRESS) ? "In Progress" : "Done"));
		if (todo.getCreatedUser() != null) {
			todoDto.setCreatedUser(todo.getCreatedUser());
			todoDto.setCreatedUsername(userRepo.findById(todo.getCreatedUser()).get().getUsername());
		} else {
			todoDto.setCreatedUsername("");
		}
		todoDto.setTargetedDate(todo.getTargetedDate());
		return todoDto;
	}

	private Todo mapToTodo(TodoDto dto) {
		Todo todo = new Todo();
		if (dto.getId() != null) {
			todo.setId(dto.getId());
			todo.setCreatedDate(todoRepo.findById(dto.getId()).get().getCreatedDate());
		} else {
			todo.setCreatedDate(new Date());
		}
		todo.setTitle(dto.getTitle());
		todo.setDescription(dto.getDescription());
		todo.setDetail(dto.getDetail());
		todo.setStatus(dto.getStatus());
		todo.setCreatedUser(dto.getCreatedUser());
		todo.setTargetedDate(dto.getTargetedDate());
		todo.setUpdatedDate(new Date());
		return todo;
	}

	@Override
	@Transactional
	public void extendTargetDate(Long id, Integer total) {
		Todo todo = todoRepo.findById(id).get();
		Calendar cal = Calendar.getInstance();
		cal.setTime(todo.getTargetedDate());
		cal.add(Calendar.DATE, total);
		todoRepo.extendTargetDate(id, cal.getTime());
	}

}
