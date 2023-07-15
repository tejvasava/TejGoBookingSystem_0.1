package com.tejgo.main.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CabDto {

	private Long cabId;
	
	private String cabNumber;
	
	private String model;
	
	private String year;
	
	private String capacity;
}
