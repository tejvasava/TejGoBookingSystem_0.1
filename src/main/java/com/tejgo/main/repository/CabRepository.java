package com.tejgo.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejgo.main.core.Cab;

@Repository
public interface CabRepository  extends JpaRepository<Cab, Long> {

	Cab findByCabNumber(String cabNumber);

}
