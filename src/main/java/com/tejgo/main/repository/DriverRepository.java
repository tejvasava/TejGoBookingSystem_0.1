package com.tejgo.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejgo.main.core.Driver;
import com.tejgo.main.core.User;

@Repository
public interface DriverRepository  extends JpaRepository<Driver, Long>{
	
	List<Driver> findByStatus(String status);

	Driver findByUser(User user);

	Driver findByUser(Long userId);
}
