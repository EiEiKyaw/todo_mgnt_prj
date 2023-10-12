package com.testing.todo_mgnt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.testing.todo_mgnt.util.TodoStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "todos")
@NoArgsConstructor
public class Todo implements Serializable {

	private static final long serialVersionUID = -2458146559539810081L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String detail;

	@Enumerated(EnumType.STRING)
	private TodoStatus status;
	
	@Column(name = "created_user")
	private Long createdUser;

	@Column(name = "targeted_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date targetedDate;

	@Column(name = "created_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdDate;

	@Column(name = "updated_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date updatedDate;

	@PrePersist
	public void setInitialValue() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
		this.status = TodoStatus.TODO;
	}

	public Todo orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}