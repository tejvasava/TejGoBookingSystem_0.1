package com.tejgo.main.service;

import com.tejgo.main.dto.DriverDto;
import com.tejgo.main.dto.ResponseVO;

public interface DriverService {

	ResponseVO<DriverDto> addEditDriver(DriverDto driverDto);

	ResponseVO<DriverDto> addEditDriverStatus(String status,Long id);

	DriverDto getDriver(Long id);

}
