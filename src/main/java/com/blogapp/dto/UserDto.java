package com.blogapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {

	private Long id;

	@NotEmpty(message = "Name cannot be empty")
	private String name;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Must be an email address")
	private String email;
	
	@NotEmpty(message = "Password cannot be empty")
	private String password;

	public UserDto(@NotEmpty(message = "Name cannot be empty") String name, @NotEmpty(message = "Email cannot be empty") @Email String email,
			@NotEmpty(message = "Password cannot be empty") String password) {
		
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	
	
	public UserDto() {
	
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
	
}
