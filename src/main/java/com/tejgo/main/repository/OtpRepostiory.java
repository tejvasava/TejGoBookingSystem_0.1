package com.tejgo.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejgo.main.core.OTP;
import com.tejgo.main.core.User;

public interface OtpRepostiory extends JpaRepository<OTP, Long>{

	Optional<OTP> findByOtp(Long otp);

	Optional<OTP> findByOtpAndUser(String otp, User user);

}
