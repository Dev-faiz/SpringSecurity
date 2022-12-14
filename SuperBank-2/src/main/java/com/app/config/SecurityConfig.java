package com.app.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
	// In memory approach 1 
	@Bean
	public InMemoryUserDetailsManager inMemoryUser() {
		
//		UserDetails  admin = User.withDefaultPasswordEncoder()
//				.username("faiz")
//				.password("8080")
//				.authorities("admin")
//				.build();
//		
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("8080")
//				.authorities("user")
//				.build();
		
		// approach 2 using NoOp password encoder 
		
		UserDetails  admin = User.withUsername("faiz")
				.password("8080")
				.authorities("admin")
				.build();
		
		UserDetails user = User.withUsername("user")
				.password("8080")
				.authorities("user")
				.build();
		
		
		return new InMemoryUserDetailsManager( admin , user  ) ;
		
	}
	
	@Bean
	public PasswordEncoder defaultPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance() ;
	}
}
