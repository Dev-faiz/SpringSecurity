package com.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.app.entity.Authority;
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
				
				
		
				return new UsernamePasswordAuthenticationToken(username , password , grantAuth(customer.get(0).getAuthorities()) );
				
				
				
			}else {
				throw new BadCredentialsException("Invalid Password");
			}
			
			
			
		}else {
			throw new BadCredentialsException("User is not exist");
		}
		

		
	}

	private List<GrantedAuthority> grantAuth(Set<Authority> auth ){
		List<GrantedAuthority> authoroties = new ArrayList<>() ;
		
		for( Authority a : auth) {
			authoroties.add(new SimpleGrantedAuthority( a.getName() ));
		}
		
		return authoroties ;
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
