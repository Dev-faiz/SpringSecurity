package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {
	
	@GetMapping("notices")
	public String getMyacccount() {return "Welcome to notice page" ; }
	
	
}
