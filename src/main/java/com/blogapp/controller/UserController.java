package com.blogapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogapp.dto.UserDto;
import com.blogapp.model.User;
import com.blogapp.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLogin() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("user", new UserDto());
	    return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
		User existing = userService.findByEmail(user.getEmail());
		if(existing != null) {
			result.rejectValue("email", null, "Email Already Exists");
		}
		if(result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}
		userService.saveUser(user);
		return "redirect:/register?success";
	}
	
	@GetMapping("/user/profile/{userId}")
	public String showUserProfile(@PathVariable("userId") Long uid, Model model) {
		User user = userService.findUser(uid);
		model.addAttribute("user", user);
		return "showProfile";
	}
	
	@GetMapping("/user/edit_profile/{userId}")
	public String editUserProfile(@PathVariable("userId") Long uid, Model model) {
		User user = userService.findUser(uid);
		model.addAttribute("user",user);
		return "editProfile";
	}
	
	@PutMapping("/user/edit_profile/{userId}")
	public String updateUserProfile(@Valid @ModelAttribute("user") UserDto userdto, BindingResult result, @PathVariable("userId") Long uid, Model model) {
		userdto.setId(uid);
		if(result.hasFieldErrors("name") || result.hasFieldErrors("email")) {
			return "editProfile";
		}
		userService.updateUser(userdto, uid);
		return "redirect:/user/profile/" + uid;
	}
	
	@GetMapping("/user/delete_profile/{userId}")
	public String deleteUserProfile(@PathVariable("userId") Long uid) {
		userService.deleteUser(uid);
		return "redirect:/logout";
	}
	
	@GetMapping("/user/profile/updatePassword/{userId}")
	public String showUpdatePassWord(@PathVariable("userId") Long uid, Model model){
		UserDto user = new UserDto();
		user.setId(uid);
		model.addAttribute("user", user);
		return "forgetPassword";
	}
	
	@PostMapping("/user/profile/updatePassword/{userId}")
	public String updateUserPassword(Model model, @PathVariable("userId") Long uid,
			@Valid @ModelAttribute("user") UserDto userdto,
			BindingResult result, 
			@RequestParam("oldPassword") String oldPass) {
		
		userdto.setId(uid);
		Boolean res = userService.updatePassword(uid, userdto, oldPass);
		if(userdto.getPassword() == "") {
			model.addAttribute("errMessage", "Please fill New password");
			return "forgetPassword";
		}
		if(!res || oldPass.isEmpty()) {
			model.addAttribute("errMessage", "Old Password is not correct");
			return "forgetPassword";
		}
		if(result.hasFieldErrors("password")) {
			return "forgetPassword";
		}
		return "redirect:/user/profile/" + uid;
	}
}
