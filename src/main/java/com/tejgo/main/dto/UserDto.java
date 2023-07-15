package com.tejgo.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserDto {

	private Long employeeId;
	
	private String firstName;
	 
	private String lastName;
	  
	private String email;
	
	private String phoneNumber;
	  
	private String address;
	  
	private Long userRoleId;
	
	private String password;
}
