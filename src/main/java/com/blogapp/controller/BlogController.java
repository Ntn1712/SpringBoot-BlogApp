package com.blogapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogapp.dto.BlogDto;
import com.blogapp.model.Blog;
import com.blogapp.service.BlogService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/add_blog")
	public String showBlogPage(Model model) {
		model.addAttribute("blog", new BlogDto());
		return "blogs/addNew";
	}
	
	@PostMapping("/add_blog")
	public String addNewBlog(@Valid @ModelAttribute("blog") BlogDto blogdto, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("blog", blogdto);
			return "blogs/addNew";
		}
		blogService.saveBlog(blogdto);
		return "redirect:/blogs";
	}
	
	@GetMapping("")
	public String showAllBlogs(Model model) {
		List<Blog> blogs = blogService.findAll();
		if(blogs.isEmpty()) {
			model.addAttribute("emptyMessage", "There are no blogs to show");
		}
		model.addAttribute("blogs", blogs);
		return "blogs/showAllBlogs";
	}
	
	@GetMapping("/my_blogs")
	public String showMyBlogs(Model model) {
		List<Blog> blogs = blogService.findByAuthor();
		if(blogs.isEmpty()) {
			model.addAttribute("emptyMessage", "You don't have any blogs");
		}
		model.addAttribute("blogs", blogs);
		return "blogs/showMyblogs";
	}

	@GetMapping("/my_blogs/edit_blog/{bid}")
	public String showEditPage(Model model, @PathVariable("bid") Long bid) {
		Blog blog = blogService.getBlogById(bid);
		model.addAttribute("blog", blog);
		return "blogs/editBlog";
	}

	@PutMapping("/my_blogs/edit_blog/{blogid}")
	public String editBlog(@Valid @ModelAttribute("blog") BlogDto blogdto, BindingResult result, Model model, @PathVariable("blogid") Long bid){
		blogdto.setId(bid);
		if(result.hasErrors()) {
			model.addAttribute("blogdto", blogdto);
			return "blogs/editBlog";
		}
		blogService.saveEditBlog(bid, blogdto);
		return "redirect:/blogs/my_blogs";
	}
	
	@GetMapping("/my_blogs/delete_blog/{blogid}")
	public String deleteMyBlog(@PathVariable("blogid") Long bid) {
		blogService.deleteBlog(bid);
		return "redirect:/blogs/my_blogs";
	}
	
	@GetMapping("/{blogid}")
	public String showblog(@PathVariable("blogid") Long bid, Model model) {
		Blog blog = blogService.getBlogById(bid);
		List<Blog> blogs = blogService.findAll();
		model.addAttribute("blogs", blogs);
		model.addAttribute("blog", blog);
		return "blogs/showBlog";	
	}
}
