package com.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.entity.Customer;
import com.app.entity.SecurityCustomer;
import com.app.repo.CustomerRepository;

public class SuperBankUserDetails implements UserDetailsService {

	  @Autowired
	   private CustomerRepository customerRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        List<Customer> customer = customerRepository.findByEmail(username);
	        if (customer.size() == 0) {
	            throw new UsernameNotFoundException("User details not found for the user : " + username);
	        }
	        return new SecurityCustomer(customer.get(0));
	    }
	
	
}
