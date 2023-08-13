package com.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blogapp.dto.BlogDto;
import com.blogapp.exception.UserNotAuthorized;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.model.Blog;
import com.blogapp.model.User;
import com.blogapp.repository.BlogRepository;
import com.blogapp.security.CustomUserDetails;

import jakarta.validation.Valid;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepo;
	
	@Autowired 
	private UserService userService;
	
	
	private CustomUserDetails userDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (CustomUserDetails) authentication.getPrincipal();
	}
	
	public void saveBlog(@Valid BlogDto blogdto) {
		User user = userService.findUser(userDetails().getId());
		Blog blog = new Blog();
		blog.setTitle(blogdto.getTitle());
		blog.setImgUrl(blogdto.getImgUrl());
		blog.setDescription(blogdto.getDescription());
		blog.setAuthor(user);
		blogRepo.save(blog);

	}

	public List<Blog> findAll() {
		return blogRepo.findAll();
	}

	public List<Blog> findByAuthor() {
		Long userId = userDetails().getId();
		List<Blog> blogs = blogRepo.findByAuthor(userId);
		return blogs;
	}

	public void saveEditBlog(Long bid, BlogDto blogdto) {
		if(blogRepo.findById(bid).isEmpty()) {
			throw new UserNotFoundException("Blog does not exist");
		}
		Blog blog = blogRepo.findById(bid).get();
		blog.setTitle(blogdto.getTitle());
		blog.setImgUrl(blogdto.getImgUrl());
		blog.setDescription(blogdto.getDescription());
		blogRepo.save(blog);
	}

	public void deleteBlog(Long bid) {
		Long id = userDetails().getId();
		Blog blog = blogRepo.findById(bid).get();
		Long uid = blog.getAuthor().getId();
		if(!uid.equals(id)) {
			throw new UserNotAuthorized("User Not Authorized");
		}
		blogRepo.deleteById(bid);
	}

	public Blog getBlogById(Long bid) {
		return blogRepo.getReferenceById(bid);
	}
	
}
