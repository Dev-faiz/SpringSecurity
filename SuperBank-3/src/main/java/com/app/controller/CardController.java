package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	
	@GetMapping("myCard")
	public String getMyacccount() {return "Welcome to contact page" ; }
	
}
