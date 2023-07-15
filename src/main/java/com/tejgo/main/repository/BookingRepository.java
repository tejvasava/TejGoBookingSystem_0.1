package com.tejgo.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tejgo.main.core.Booking;
import com.tejgo.main.core.Driver;
import com.tejgo.main.core.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	Optional<Booking> findByUserAndStatus(User userId, String status);

	Booking findByUserAndStatus(Long userId, String status);

	
	@Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.status = :status")
	List<Booking> findByUserIdAndStatus( @Param("userId") Long userId, @Param("status") String status);

	
	 List<Booking> findByDriverAndStatus(Driver driver, String status);

	 
}
