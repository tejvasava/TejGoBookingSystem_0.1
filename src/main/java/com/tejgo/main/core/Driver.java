package com.tejgo.main.core;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Access(AccessType.FIELD)
@Table(name = "Driver")
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driver_id")
	private Long driverId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "license_number")
	private String licenseNumber;
	
	@Column(name = "cab_number")
	private String cabNumber;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
