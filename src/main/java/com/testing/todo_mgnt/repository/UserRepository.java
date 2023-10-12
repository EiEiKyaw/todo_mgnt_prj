package com.testing.todo_mgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.testing.todo_mgnt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.username = ?1 or u.email = ?2")
	User findByUsernameOrEmail(String username, String email);

}
