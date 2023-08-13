package com.blogapp.exception;

public class UserNotAuthorized extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotAuthorized(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserNotAuthorized(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
