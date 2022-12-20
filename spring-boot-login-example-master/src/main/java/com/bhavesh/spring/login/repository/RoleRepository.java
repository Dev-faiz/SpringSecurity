package com.bhavesh.spring.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhavesh.spring.login.models.ERole;
import com.bhavesh.spring.login.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
