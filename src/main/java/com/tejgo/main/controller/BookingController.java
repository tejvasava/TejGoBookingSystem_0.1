package com.tejgo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tejgo.main.dto.BookingDto;
import com.tejgo.main.dto.OTPDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.RideResponse;
import com.tejgo.main.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR') or hasAuthority('CUSTOMER')")
	public  ResponseVO<BookingDto>  searchCabDriver(@RequestBody BookingDto bookingDto) {
		return bookingService.searchCabDriver(bookingDto);
	}
	
	
	@PostMapping("/confimOtp") 
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DRIVER')")
	public  ResponseVO<OTPDto>  startRide(@RequestParam(value = "otp") String Otp) {
		return bookingService.validOtpAndstartRide(Otp);
	}
	
	@PostMapping("/endRide") 
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DRIVER') or hasAuthority('CUSTOMER')")
	public  ResponseVO<RideResponse>  endRide() {
		return bookingService.endstartRide();
	}
	
}
