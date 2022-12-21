
package com.app.repo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Contact;



@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
