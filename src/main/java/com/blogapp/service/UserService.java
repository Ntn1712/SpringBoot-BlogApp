package com.blogapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.dto.UserDto;
import com.blogapp.exception.UserNotAuthorized;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.model.User;
import com.blogapp.repository.UserRepository;
import com.blogapp.security.CustomUserDetails;

import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Bean
	private BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	private CustomUserDetails userDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (CustomUserDetails) authentication.getPrincipal();
	}
	
	public void saveUser(@Valid UserDto userdto) {
		User user = new User();
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(encode().encode(userdto.getPassword()));
		userRepo.save(user);
	}

	public User findUser(Long id) {
		if(userRepo.findById(id).isEmpty()) {
			throw new UserNotFoundException("User Does Not Exist");
		}
		return userRepo.findById(id).get();
		
	}

	public void updateUser(UserDto userdto, Long uid) {
		if(userRepo.findById(uid).isEmpty()) {
			throw new UserNotFoundException("User Does Not Exist");
		}
		User user = userRepo.findById(uid).get();
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		userRepo.save(user);
	}

	public void deleteUser(Long uid) {
		Long id = userDetails().getId();
		if(!uid.equals(id)) {
			throw new UserNotAuthorized("User Not Authorized");
		}
		userRepo.deleteById(uid);
	}

	public Boolean updatePassword(Long uid, UserDto userdto, String oldPass) {
		if(userRepo.findById(uid).isEmpty()) {
			throw new UserNotFoundException("User Does Not Exist");
		}
		User user = userRepo.findById(uid).get();
		Boolean passMatch = encode().matches(oldPass, user.getPassword());
		if(passMatch && userdto.getPassword() != "") {
			user.setPassword(encode().encode(userdto.getPassword()));
			userRepo.save(user);
			return true;
		} else {
			return false;
		}	
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}
