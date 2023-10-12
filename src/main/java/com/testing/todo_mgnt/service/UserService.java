package com.testing.todo_mgnt.service;

import java.util.List;

import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.entity.User;

public interface UserService {
	void create(UserDto userDto);

	List<UserDto> getAll(UserDto user);

	UserDto findById(long id);

	UserDto findByUsername(String email);

	UserDto findByEmail(String username);

	User findByUsernameOrEmail(String username, String email);

	UserDto getLoginUser();

	void update(UserDto user);
}
