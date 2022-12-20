package com.app.config;



import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain getConfigure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				(auth)-> 
				auth.requestMatchers("/myAccount" , "/myBalance" , "/myCard" , "/myLoan").authenticated()
				.requestMatchers("/contact" , "/notices" , "/register" ).permitAll()
				);
		
		
		http.csrf().disable();
		http.formLogin();
		http.httpBasic() ; 
		return http.build() ; 
	}
	
	@Bean
	public PasswordEncoder defaultPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
