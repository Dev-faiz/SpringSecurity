package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {
	
	@GetMapping("myLoan")
	public String myLoanControllerHandler() {return "My loan details from database";}
	
	
}
