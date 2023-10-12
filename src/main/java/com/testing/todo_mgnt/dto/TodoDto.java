package com.testing.todo_mgnt.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.testing.todo_mgnt.util.TodoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

	private Long id;

	private String title;

	private String description;

	private String detail;

	private TodoStatus status;

	private String statusText;

	private Long createdUser;

	private String createdUsername;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date targetedDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date updatedDate;

}