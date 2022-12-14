package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.repo.CustomerRepo;

@RestController
public class LoginController {

	@Autowired
	private CustomerRepo customerRepo ; 
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer c){
		
		Customer savedCustomer = null ; 
		ResponseEntity<String> response = null ; 
		
		try{
			savedCustomer = customerRepo.save(c);
			if(savedCustomer.getId() > 0) {
				response = ResponseEntity.status(HttpStatus.CREATED).body("User registed successfully");
			}
		}catch(Exception e ) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occured due to "+ e.getMessage());
		}
		return response ; 
		
	}
}
