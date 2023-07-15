package com.tejgo.main.enums;

public enum DriverStatus {

	
	ACTIVE("Active"),
	INACTIVE("Inactive");
	
    private final String name;

    private DriverStatus(String value) {
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
