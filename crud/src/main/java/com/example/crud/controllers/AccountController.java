package com.example.crud.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.crud.models.AppUser;
import com.example.crud.models.RegisterDto;
import com.example.crud.repositories.AppUserRepository;

import jakarta.validation.Valid;

@Controller
public class AccountController {

	@Autowired
	private AppUserRepository repo;
	
	
	
	@GetMapping("/profile")
	public String profile(Authentication auth, Model model) {
		AppUser user = repo.findByEmail(auth.getName());
		model.addAttribute("appUser", user);
		
		return "profile";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String register(Model model) {
		RegisterDto registerDto = new RegisterDto();
		model.addAttribute(registerDto);
		model.addAttribute("success", false);
		return "register";
	}
	
	
	@PostMapping("/register")
	public String register(
			Model model,
			@Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result
			) {
		
		if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
    		result.addError(
    				new FieldError("registerDto", "confirmPassword"
    						, "Password and Confirm Password do not match")
    		);
    	}
		
		
		AppUser appUser = repo.findByEmail(registerDto.getEmail());
		if (appUser != null) {
			result.addError(
    				new FieldError("registerDto", "email"
    						, "Email address is already used")
    		);
		}
		
		
		if (result.hasErrors()) {
    		return "register";
    	}
		
		
		try {
			// create new account
			var bCryptEncoder = new BCryptPasswordEncoder();
			
			
			AppUser newUser = new AppUser();
			newUser.setFirstName(registerDto.getFirstName());
			newUser.setLastName(registerDto.getLastName());
			newUser.setEmail(registerDto.getEmail());
			newUser.setPhone(registerDto.getPhone());
			newUser.setAddress(registerDto.getAddress());
			newUser.setRole("client");
			newUser.setCreatedAt(new Date());
			newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
			
			repo.save(newUser);
			
			
			model.addAttribute("registerDto", new RegisterDto());
			model.addAttribute("success", true);
		}
		catch(Exception ex) {
			result.addError(
    				new FieldError("registerDto", "firstName"
    						, ex.getMessage())
    		);
		}
		
		return "register";
	}
}
