package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeToController {
	
	@GetMapping("welcome")
	public String welcome() {
		
		return "Welcome to Spring with Security" ; 
		
	}
}
