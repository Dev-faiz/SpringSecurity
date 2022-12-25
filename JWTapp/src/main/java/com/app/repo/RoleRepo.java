package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	Role findByName(String name);
	
	
}
