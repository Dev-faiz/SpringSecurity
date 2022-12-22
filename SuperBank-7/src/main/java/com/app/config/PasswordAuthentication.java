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
		   String username = authentication.getName();
		   String pwd = authentication.getCredentials().toString();
		   List<Customer> customer = customerRepo.findByEmail(username);
		   if (customer.size() > 0) {
		      if (passwordEncoder.matches(pwd, customer.get(0).getPwd())) {
		           return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer.get(0).getAuthorities()));
		      } else {
		           throw new BadCredentialsException("Invalid password!");
		      }
		   }else {
		            throw new BadCredentialsException("No user registered with this details!");
		        }
	}

	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : authorities) {
		    grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		 }
		   return grantedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}


}
