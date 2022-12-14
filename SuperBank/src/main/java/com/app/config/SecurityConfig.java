package com.app.config;

import java.security.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain getConfigure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				(auth)-> 
				auth.requestMatchers("/myAccount" , "/myBalance" , "/myCard" , "/myLoan").authenticated()
				.requestMatchers("/contact" , "/notices" ).permitAll()
				);
		
		
		
		http.formLogin();
		http.httpBasic() ; 
		return http.build() ; 
	}
}
