package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.filter.CustomAuthenticationFilter;
import com.app.filter.CustomAuthorizationFilter;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private UserDetailsService userDetailService ; 
	
	@Autowired 
	private PasswordEncoder passwordEncoder ;
	
	@Autowired
	private AuthenticationConfiguration auth ; 
	
	@Bean
	public DaoAuthenticationProvider  configure(AuthenticationManagerBuilder auth) throws Exception {	
		DaoAuthenticationProvider  a = new DaoAuthenticationProvider();
		a.setUserDetailsService(userDetailService);
		a.setPasswordEncoder(passwordEncoder);	
		return a ; 		
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {
		
		CustomAuthenticationFilter customAuthenticationfilter = new CustomAuthenticationFilter(authManagerBuilder(auth) ); 
		customAuthenticationfilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable();
		http.authorizeHttpRequests().requestMatchers("/api/login" , "/api/token/refresh/**").permitAll();
		http.authorizeHttpRequests().requestMatchers("/api/user/**").hasAnyAuthority("ROLE_USER" ,"ROLE_SUPER_ADMIN");
		http.authorizeHttpRequests().requestMatchers("/api/user/save/**");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationfilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.formLogin();
		return http.build() ;
		
	}


	
	
	@Bean
	public AuthenticationManager authManagerBuilder(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager(); 
	}
}
