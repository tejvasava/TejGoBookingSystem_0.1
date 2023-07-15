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
@Table(name = "Booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long bookingId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "cab_id")
	private Cab cab;

	@ManyToOne
	@JoinColumn(name = "driver_id")
	private Driver driver;

	@Column(name = "pickup_location")
	private String pickupLocation;

	@Column(name = "dropoff_location")
	private String dropoffLocation;

	@Column(name = "pickup_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pickupDatetime;

	@Column(name = "dropoff_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dropoffDatetime;

	@Column(name = "fare_amount")
	private Long fareAmount;

	@Column(name = "status")
	private String status;
}
