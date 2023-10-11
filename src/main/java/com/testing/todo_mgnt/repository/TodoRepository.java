package com.testing.todo_mgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testing.todo_mgnt.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
