package com.blogapp.dto;

import com.blogapp.model.User;

import jakarta.validation.constraints.NotEmpty;

public class BlogDto {
	
	private Long id;
	
	@NotEmpty(message = "Please provide the title of the Blog")
	private String title;
	
	@NotEmpty(message = "Please provide the display image URL")
	private String imgUrl;
	
	@NotEmpty(message = "Please provide the content for the Blog")
	private String description;
	
	private User author;

	public BlogDto(@NotEmpty(message = "Please provide the title of the blog") String title,
			@NotEmpty(message = "Please provide the display image URL") String imgUrl,
			@NotEmpty(message = "Please provide the content for the Blog") String description, User author) {
		this.title = title;
		this.imgUrl = imgUrl;
		this.description = description;
		this.author = author;
	}

	public BlogDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	

}
