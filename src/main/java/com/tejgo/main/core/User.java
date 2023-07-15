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
@Table(name = "User_customer")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "first_name")
	private String firstName;
	 
	@Column(name = "last_name")
	private String lastName;
	  
	@Column(name = "email")
	private String email;
	 
	@Column(name = "phone_number")
	private String phoneNumber;
	  
	@Column(name = "address")
	private String address;
	
	@Column(name = "password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private UserRole userRole;
	 
}
