package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.MyUser;
import com.app.model.Role;
import com.app.repo.MyUserRepo;
import com.app.repo.RoleRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class MyUserServiceImpl implements MyUserService , UserDetailsService{
	
	@Autowired
	private MyUserRepo myUserRepo ;
	@Autowired
	private RoleRepo myRoleRepo ;
	
	@Autowired
	private PasswordEncoder pass ; 
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser myUser = myUserRepo.findByUserName(username);
		if(myUser == null ) {
			
			log.error("User is not found in database");
			throw new UsernameNotFoundException("User not found ");
		}else {
			log.info("user is founded");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		myUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())) );
		return new User(myUser.getUserName(), myUser.getPassword(), authorities ) ; 
		
		
	}

	@Override
	public MyUser saveMyUser(MyUser user) {
		user.setPassword( pass.encode(user.getPassword()) );
		return myUserRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return myRoleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolName) {
		log.info("Saving new role {} to the database " , rolName , username);
		
		MyUser myUser = myUserRepo.findByUserName(username);
		Role role = myRoleRepo.findByName(rolName);
		myUser.getRoles().add(role);
	}

	@Override
	public MyUser getUser(String userName) {
		log.info("fetching user {} " ,   userName);
		MyUser myUser = myUserRepo.findByUserName(userName);
		return myUser;
	}

	@Override
	public List<MyUser> getAllUser() {
		log.info("fetching all user");
		return myUserRepo.findAll();
	}



}
