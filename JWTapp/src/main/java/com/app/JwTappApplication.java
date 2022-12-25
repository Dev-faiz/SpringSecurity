package com.app;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.model.MyUser;
import com.app.model.Role;
import com.app.service.MyUserService;

@SpringBootApplication
public class JwTappApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwTappApplication.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(MyUserService myUserService) {
//		
//		return args ->{
//			
//			myUserService.saveRole(new Role( null , "ROLE_USER"));
//			myUserService.saveRole(new Role( null , "ROLE_ADMIN"));
//			myUserService.saveRole(new Role( null , "ROLE_MANAGER"));
//			myUserService.saveRole(new Role( null , "ROLE_SUPER_ADMIN"));
//			
//			myUserService.saveMyUser( new MyUser(null , "FAIZ" , "faiz123" , "8080" , new ArrayList<>()) );
//			myUserService.saveMyUser( new MyUser(null , "ZAID" , "zaid123" , "8080" , new ArrayList<>()) );
//			myUserService.saveMyUser( new MyUser(null , "MRIGANKA" , "mri123" , "8080" , new ArrayList<>()) );
//			myUserService.saveMyUser( new MyUser(null , "ROHIT" , "rohit123" , "8080" , new ArrayList<>()) );
//			
//			myUserService.addRoleToUser("faiz123", "ROLE_SUPER_ADMIN");
//			myUserService.addRoleToUser("zaid123", "ROLE_MANAGER");
//			myUserService.addRoleToUser("zaid123", "ROLE_MANAGER");
//			myUserService.addRoleToUser("mri123", "ROLE_ADMIN");
//			myUserService.addRoleToUser("mri123", "ROLE_USER");
//			myUserService.addRoleToUser("rohit123", "ROLE_ADMIN");
//		
//			
//			
//		};
//		
//	}
	
	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
