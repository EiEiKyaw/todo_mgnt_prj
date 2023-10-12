package com.testing.todo_mgnt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.testing.todo_mgnt.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	List<Todo> findByCreatedUser(Long id);

	@Modifying
	@Query("UPDATE Todo t SET t.targetedDate = ?2 WHERE t.id = ?1")
	void extendTargetDate(long id, Date time);

}
