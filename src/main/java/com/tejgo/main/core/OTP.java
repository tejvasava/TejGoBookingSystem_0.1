package com.tejgo.main.core;

import java.util.Date;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Access(AccessType.FIELD)
@Table(name = "OTP")
public class OTP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "otp_id")
	private Long otpId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "otp")
	private String otp;

	@Column(name = "generated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date generatedAt;

	@Column(name = "is_verified")
	private boolean isVerified;

	@Column(name = "verification_attempts")
	private Long verificationAttempts;

}
