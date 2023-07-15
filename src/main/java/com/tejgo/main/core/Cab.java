package com.tejgo.main.core;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Access(AccessType.FIELD)
@Table(name = "Cab")
public class Cab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cab_id")
	private Long cabId;
	
	@Column(name = "cab_number")
	private String cabNumber;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "capacity")
	private String capacity;
	
}
