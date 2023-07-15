package com.tejgo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tejgo.main.dto.DriverDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.service.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	private DriverService driverService;
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')  or hasAuthority('DRIVER')")
	public  ResponseVO<DriverDto>  addEditDriver(@RequestBody DriverDto driverDto) {
		return driverService.addEditDriver(driverDto);
	}
	
	@PostMapping("/status")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR') or hasAuthority('DRIVER')")
	public  ResponseVO<DriverDto>  addEditDriverStatus(@RequestParam ("status") String status,@RequestParam(value = "id") Long id) {
		return driverService.addEditDriverStatus(status,id);
	}
	
	@GetMapping("/id")
	public DriverDto  getDriver(@RequestParam("id") Long id) {
		return driverService.getDriver(id);
	}
}
