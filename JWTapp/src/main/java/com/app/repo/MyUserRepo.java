package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.MyUser;

public interface MyUserRepo extends JpaRepository<MyUser, Integer> {
	
	MyUser findByUserName(String userName);

}
