package com.tejgo.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DriverDto {
	
	private Long driverId;
	
	private String name;
	
	private String phoneNumber;
	
	private String licenseNumber;
	
	private String cabNumber;
	
	private String status;
	
}
