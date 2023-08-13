package com.blogapp.exception;

import org.springframework.http.HttpStatus;

public class CustomExceptionDetails {
	
	private String message;
	
	private Throwable throwable;
	
	private HttpStatus httpStatus;

	public CustomExceptionDetails(String message, Throwable throwable, HttpStatus httpStatus) {
		this.message = message;
		this.throwable = throwable;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	

}
