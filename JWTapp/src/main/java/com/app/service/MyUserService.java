package com.app.service;

import java.util.List;

import com.app.model.MyUser;
import com.app.model.Role;

public interface MyUserService {

	MyUser saveMyUser(MyUser user);
	
	Role saveRole(Role role);
	
	void addRoleToUser(String username , String rolName);
	
	MyUser getUser(String userName);
	
	List<MyUser> getAllUser();
	
	
}
