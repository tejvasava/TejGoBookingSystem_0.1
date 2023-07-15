package com.tejgo.main.enums;

public enum RideStatus {

	
	AVAILABLE("AVAILABLE"),
	BUSY("BUSY"),
	BOOKED("BOOKED"),
	ENROUTE("ENROUTE"), 
	COMPLETED("COMPLETED"),
	CANCELED("CANCELED");
	
    private final String name;

    private RideStatus(String value) {
        this.name = value;
    }

    public String value() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
