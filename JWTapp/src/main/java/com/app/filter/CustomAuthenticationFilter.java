package com.app.filter;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authManager ; 
	
	public CustomAuthenticationFilter(AuthenticationManager m ) {
		this.authManager = m ; 
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String userName = request.getParameter(getUsernameParameter());
		String password = request.getParameter(getPasswordParameter());
		log.info("Username is " , userName );
		log.info("Password is " , password);
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);
		
		return authManager.authenticate(authToken) ; 
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		
		User user = (User) authentication.getPrincipal();
		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes() );
		String accessToken = JWT.create()
							.withSubject(user.getUsername())
							.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000 ) )
							.withIssuer(request.getRequestURL().toString())
							.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority:: getAuthority).collect(Collectors.toList() ))
							.sign(algorithm);

		
		String refreshToken = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000 ) )
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		
		response.setHeader("accessToken", accessToken);
		response.setHeader("refreshToken", refreshToken);
		
		Map<String , String > tokens = new HashMap<>();
		tokens.put("accessToken",accessToken);
		tokens.put("refreshToken", refreshToken);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}
	
	
	
}
