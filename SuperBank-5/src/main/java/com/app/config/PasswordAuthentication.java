package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.entity.Customer;
import com.app.repo.CustomerRepository;


@Component
public class PasswordAuthentication implements AuthenticationProvider {

	@Autowired
	private CustomerRepository customerRepo ; 
	
	@Autowired
	private PasswordEncoder passwordEncoder ; 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName() ;
		String password = authentication.getCredentials().toString() ; 
		
		List<Customer> customer = customerRepo.findByEmail(username);
		
		if(!customer.isEmpty()) {
			
			if(passwordEncoder.matches(password, customer.get(0).getPwd())) {
				
				List<GrantedAuthority> authoroties = new ArrayList<>() ;
				authoroties.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(username , password , authoroties);
				
				
				
			}else {
				throw new BadCredentialsException("Invalid Password");
			}
			
			
			
		}else {
			throw new BadCredentialsException("User is not exist");
		}
		
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// tell it that we want to support username ans password authentication
		
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
