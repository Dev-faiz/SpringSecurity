package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.entity.Customer;
import com.app.repo.CustomerRepo;

@Service
public class SuperBankUserDetails implements UserDetailsService {
	
	@Autowired
	private CustomerRepo customerRepo ; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//defining username and password 
		String userName , password ;
		
		//creating an array of authoroties 
		List<GrantedAuthority> authorities = null ; 
		
		//find all emails 
		List<Customer> customer = customerRepo.findByEmail(username);
		
		// check whether list contain element or not 
		if(customer.size()==0) throw new  UsernameNotFoundException("No user is available in database");
		else {
			// assign first founded value as user name and password 
			userName = customer.get(0).getEmail();
			password = customer.get(0).getPwd();
			authorities = new ArrayList<>();
			
			// assign authorities 
			authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
			
			
		}
		// return new User for spring security 
		return new User(username , password , authorities);
	}

}
