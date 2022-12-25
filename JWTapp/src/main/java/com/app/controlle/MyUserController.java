package com.app.controlle;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.model.AddUserToRoleDTO;
import com.app.model.MyUser;
import com.app.model.Role;
import com.app.service.MyUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MyUserController {
	
	@Autowired
	private MyUserService myUserService ;
	
	@GetMapping("/user")
	public ResponseEntity<List<MyUser>> getAllUserHandler(){
		
		return  ResponseEntity.ok().body(myUserService.getAllUser());
		
	}
	
	@PostMapping("/user/save")
	public ResponseEntity<MyUser> saveUserHandler(@RequestBody MyUser user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return  ResponseEntity.created(uri).body(myUserService.saveMyUser(user));
		
	}
	
	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRoleHandler(@RequestBody Role role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return  ResponseEntity.created(uri).body(myUserService.saveRole(role));
		
	}
	
	@PostMapping("/role/addtouser")
	public ResponseEntity<Role> AddRoleToUserHandler(@RequestBody AddUserToRoleDTO role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/addtouser").toUriString());
		
		myUserService.addRoleToUser(role.getUserName(), role.getRole());
		
		return  ResponseEntity.created(uri).build();
		
	}
	
	@GetMapping("/token/refresh")
	public void refreshTokenHandler(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			try {
				String accessToken = authorizationHeader.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodeJWT = verifier.verify(accessToken); 
				String userName = decodeJWT.getSubject() ;
				MyUser myUser = myUserService.getUser(userName);
				
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes() );
				String refreshToken = JWT.create()
									.withSubject(myUser.getUserName())
									.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000 ) )
									.withIssuer(request.getRequestURL().toString())
									.withClaim("roles", myUser.getRoles().stream().map(Role:: getName).collect(Collectors.toList() ))
									.sign(algorithm);

			
				response.setHeader("accessToken", accessToken);
				response.setHeader("refreshToken", refreshToken);
				
				Map<String , String > tokens = new HashMap<>();
				tokens.put("accessToken",accessToken);
				tokens.put("refreshToken", refreshToken);
				
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
				
				
			} catch (Exception e) {
				
				log.error("Error in loggin in {} " , e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
//				response.sendError(HttpStatus.FORBIDDEN.value());
				
				Map<String , String > error = new HashMap<>();
				error.put("error-message", e.getMessage());
			
				
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error );
				
			}
			
		}else {
			throw new Exception("error in throwing exception ");
		}
	}

}
