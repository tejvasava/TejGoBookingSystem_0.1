package com.tejgo.main.dto;

import lombok.Data;

@Data
public class RideResponse {

	String totalFareAmount;
	String journyStartTime;
	String journyEndTime;
	String pickupLocation;
	String dropLocation;
}
