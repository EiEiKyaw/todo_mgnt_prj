package com.testing.todo_mgnt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.testing.todo_mgnt.dto.UserDto;
import com.testing.todo_mgnt.entity.Role;
import com.testing.todo_mgnt.entity.User;
import com.testing.todo_mgnt.repository.RoleRepository;
import com.testing.todo_mgnt.repository.UserRepository;
import com.testing.todo_mgnt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder pwdEncoder;

	@Override
	public void create(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		if (userDto.getId() != null) {
			User oldUser = userRepo.findById(userDto.getId()).get();
			user.setPassword(oldUser.getPassword());
		} else {
			user.setPassword(pwdEncoder.encode(userDto.getPassword()));
		}
		user.setStatus(userDto.getStatus());

		Role role = roleRepo.findByName(userDto.getRole());
		if (role == null) {
			role = checkRoleExist(userDto.getRole());
		}
		user.setRoles(Arrays.asList(role));
		userRepo.save(user);
	}

	@Override
	public List<UserDto> getAll(UserDto user) {
		List<User> users = new ArrayList<>();
		if (user.getRole().contains("Admin")) {
			users = userRepo.findAll();
		} else {
			User nowUser = userRepo.findById(user.getId()).get();
			users.add(nowUser);
		}
		return users.stream().map((obj) -> UserDto.mapToUserDto(obj)).collect(Collectors.toList());
	}

	private Role checkRoleExist(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return roleRepo.save(role);
	}

	@Override
	public UserDto findById(long id) {
		Optional<User> entity = userRepo.findById(id);
		if (!entity.isPresent()) {
			throw new IllegalArgumentException("Invalid user id:" + id);
		}
		return UserDto.mapToUserDto(entity.get());
	}

	@Override
	public UserDto findByEmail(String email) {
		return UserDto.mapToUserDto(userRepo.findByEmail(email));
	}

	@Override
	public UserDto findByUsername(String username) {
		return UserDto.mapToUserDto(userRepo.findByUsername(username));
	}

	@Override
	public User findByUsernameOrEmail(String username, String email) {
		System.out.println(userRepo.findByUsernameOrEmail(username, email));
		return userRepo.findByUsernameOrEmail(username, email);
	}

	@Override
	public UserDto getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepo.findByEmail(authentication.getName());
		return UserDto.mapToUserDto(user);
	}

	@Override
	public void update(UserDto userDto) {
		User user = userRepo.findById(userDto.getId()).get();
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setPassword(user.getPassword());
		newUser.setUsername(userDto.getUsername());
		newUser.setEmail(userDto.getEmail());
		newUser.setStatus(userDto.getStatus());
		Role role = roleRepo.findByName(userDto.getRole());
		if (role == null) {
			role = checkRoleExist(userDto.getRole());
		}
		newUser.setRoles(Arrays.asList(role));
		userRepo.save(newUser);
	}

}
