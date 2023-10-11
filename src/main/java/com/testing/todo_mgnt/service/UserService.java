package com.testing.todo_mgnt.service;

import java.util.List;

import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.entity.User;

public interface UserService {
	void saveUser(UserDto userDto);

	User findByUsername(String email);

	List<UserDto> findAllUsers();
}
