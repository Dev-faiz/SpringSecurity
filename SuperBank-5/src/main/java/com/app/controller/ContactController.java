package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
	
	@GetMapping("contact")
	public String getMyacccount() {return "Welcome to contact page" ; }
	
}
