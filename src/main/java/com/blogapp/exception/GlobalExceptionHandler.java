package com.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException){
		CustomExceptionDetails userException = new CustomExceptionDetails(
				userNotFoundException.getMessage(), 
				userNotFoundException.getCause(), 
				HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(userException, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {PasswordNotMatchException.class})
	public ResponseEntity<Object> handlePasswordNotMatchException(PasswordNotMatchException passwordNotMatchException){
		CustomExceptionDetails userException = new CustomExceptionDetails(
				passwordNotMatchException.getMessage(), 
				passwordNotMatchException.getCause(), 
				HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<Object>(userException, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = {UserNotAuthorized.class})
	public ResponseEntity<Object> handleUserNotAuthorized(UserNotAuthorized userNotAuthorized){
		CustomExceptionDetails userException = new CustomExceptionDetails(
				userNotAuthorized.getMessage(), 
				userNotAuthorized.getCause(), 
				HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<Object>(userException, HttpStatus.UNAUTHORIZED);
	}

}
