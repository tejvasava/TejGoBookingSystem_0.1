package com.tejgo.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejgo.main.core.UserRole;


@Repository
public interface UserRoleRepository  extends JpaRepository<UserRole, Long>{

	UserRole findByRoleName(String roleName);

	


}
