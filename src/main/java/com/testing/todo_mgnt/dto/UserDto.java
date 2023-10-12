package com.testing.todo_mgnt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.testing.todo_mgnt.entity.User;
import com.testing.todo_mgnt.util.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;

	@NotEmpty(message = "Username should not be empty")
	private String username;

	@NotEmpty(message = "Email should not be empty")
	@Email
	private String email;

	@NotEmpty(message = "Password should not be empty")
	private String password;

	@NotEmpty(message = "Role should not be empty")
	private String role;

	private Status status;

	public static UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		if (user.getId() != null) {
			userDto.setId(user.getId());
		}
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setStatus(user.getStatus());
		userDto.setPassword(user.getPassword());
		return userDto;
	}
}
