package com.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
 
}
