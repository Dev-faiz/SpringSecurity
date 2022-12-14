package com.app.config;



import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain getConfigure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				(auth)-> 
				auth.requestMatchers("/myAccount" , "/myBalance" , "/myCards" , "/myLoans" , "/user" , "/contact").authenticated()
				.requestMatchers("/notices" , "/register" ,"/body").permitAll()
				
				
				
				);
		
		// ignoring CSRF attack for particular endPoints
		
		http.csrf().ignoringRequestMatchers("/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//		http.csrf().disable();
		
		
		// managing CORS layers so that 2 different server can communicate  
		http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration corsConfig = new CorsConfiguration();
				
					corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					corsConfig.setAllowedMethods(Collections.singletonList("*"));
					corsConfig.setAllowCredentials(true);
					corsConfig.setAllowedHeaders(Collections.singletonList("*"));
					corsConfig.setMaxAge(3600L);
					return corsConfig;
			}
		});
		
		http.formLogin();
		http.httpBasic() ; 
		return http.build() ; 
	}
	
	@Bean
	public PasswordEncoder defaultPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
