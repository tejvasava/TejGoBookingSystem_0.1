package com.tejgo.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejgo.main.core.User;
import com.tejgo.main.core.UserRole;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByEmail(String username);
	
	Optional<User> findFirstByEmail(String username);

	User findByFirstName(String username);

	Optional<User> findByPhoneNumber(String phoneNumber);

	Optional<User> findByPhoneNumberAndUserRole(String phoneNumber, Long roleId);

	Optional<User> findByPhoneNumberAndUserRole(String phoneNumber, Optional<UserRole> optionalUserRole);

}
