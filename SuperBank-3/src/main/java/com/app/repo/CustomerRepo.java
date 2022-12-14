package com.app.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer> {
	
	public List<Customer> findByEmail(String email) ;
}
