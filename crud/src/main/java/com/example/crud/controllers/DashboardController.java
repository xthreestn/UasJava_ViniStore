package com.example.crud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/user")
	public String userDashboard() {
		return "user";
	}
	
	@GetMapping("/client")
	public String clientDashboard() {
		return "client";
	}
	
}
