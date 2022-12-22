package com.app.config;



import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.app.filter.AtFilter;
import com.app.filter.AuthLogginAfterFilter;
import com.app.filter.BeforeValidationFilter;
import com.app.filter.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain getConfigure(HttpSecurity http) throws Exception {
		
		http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {// managing CORS layers so that 2 different server can communicate  
				CorsConfiguration corsConfig = new CorsConfiguration();
				
					corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					corsConfig.setAllowedMethods(Collections.singletonList("*"));
					corsConfig.setAllowCredentials(true);
					corsConfig.setAllowedHeaders(Collections.singletonList("*"));
					corsConfig.setMaxAge(3600L);
					return corsConfig;
			}
		}).and().csrf().ignoringRequestMatchers("/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and().addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new BeforeValidationFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new AuthLogginAfterFilter(), BasicAuthenticationFilter.class)
		.addFilterAt(new AtFilter(), BasicAuthenticationFilter.class)
		.authorizeHttpRequests(
				(auth)-> auth
				.requestMatchers("/myAccount" , "/myBalance" , "/myCards" , "/myLoans" , "/user" , "/contact").authenticated()
				.requestMatchers("/notices" , "/register" ,"/body").permitAll()
//				.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//				.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT" , "VIEWBALANCE")
//				.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//				.requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//				.requestMatchers("/myAccount").hasRole("USER")
//                .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
//                .requestMatchers("/myLoans").hasRole("USER")
//                .requestMatchers("/myCards").hasRole("USER")
//				.requestMatchers("/user").authenticated()
//				.requestMatchers("/register" , "/contact" , "/notices" ).permitAll()
				);
		
		
		
		

		
		
		
		;
		
		http.formLogin();
		http.httpBasic() ; 
		return http.build() ; 
	}
	
	@Bean
	public PasswordEncoder defaultPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
