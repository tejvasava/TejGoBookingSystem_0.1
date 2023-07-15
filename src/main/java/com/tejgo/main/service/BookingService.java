package com.tejgo.main.service;

import java.util.Date;
import java.util.Optional;

import com.tejgo.main.core.Booking;
import com.tejgo.main.core.User;
import com.tejgo.main.dto.BookingDto;
import com.tejgo.main.dto.OTPDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.RideResponse;

public interface BookingService {

	ResponseVO<BookingDto> searchCabDriver(BookingDto bookingDto);

	ResponseVO<OTPDto> validOtpAndstartRide(String otp);

	//Booking updatePickUpDateTime(Optional<User> user, Date generatedAtDate);

	Booking updatePickUpDateTime(Optional<User> user, Date generatedAtDate);

	ResponseVO<RideResponse> endstartRide();


}
