package com.tejgo.main.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tejgo.main.core.Driver;
import com.tejgo.main.core.User;
import com.tejgo.main.core.UserRole;
import com.tejgo.main.dto.DriverDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.enums.ResponseStatus;
import com.tejgo.main.repository.DriverRepository;
import com.tejgo.main.repository.UserRepository;
import com.tejgo.main.repository.UserRoleRepository;
import com.tejgo.main.service.DriverService;
import com.tejgo.main.utils.Messages;

@Service
public class DriverServiceImpl implements DriverService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public ResponseVO<DriverDto> addEditDriver(DriverDto driverDto) {
		try {
			
			//UserRole userRole=userRoleRepository.findByRoleName("DRIVER");
			Optional<UserRole> optionalUserRole = Optional.ofNullable(userRoleRepository.findByRoleName("DRIVER"));

			
			Optional<User> userDriver=userRepository.findByPhoneNumberAndUserRole(driverDto.getPhoneNumber(),optionalUserRole);
			
			
		//	ResponseVO resVo = validateRequest(driverDto);
			//if (resVo == null) {
				Driver driver = new Driver();
				
				Optional<User> user=userRepository.findById(userDriver.get().getUserId());
				
				if (Objects.nonNull(driverDto.getDriverId()) && Objects.nonNull(userDriver.get().getUserId())) {
					driver = driverRepository.findById(driverDto.getDriverId()).get();
					//user=userRepository.findById(userDriver.get().getUserId());
				}
				driver.setName(user.get().getFirstName());
				driver.setCabNumber(driverDto.getCabNumber());
				driver.setLicenseNumber(driverDto.getLicenseNumber());
				driver.setPhoneNumber(user.get().getPhoneNumber());
				driver.setStatus(driverDto.getStatus());
				driver.setUser(user.get());
				driverRepository.save(driver);
				return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
						driverDto.getDriverId() == null ? Messages.DRIVER_SUBMIT_SUCCESS
								: Messages.DRIVER_UPDATE_SUCCESS,
								driverDto);
			//}
			//return resVo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseVO.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.FAIL.name(),
					e.getMessage(), driverDto);
		}
	}

	private ResponseVO validateRequest(DriverDto driverDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseVO<DriverDto> addEditDriverStatus(String status,Long id) {
		Optional<Driver> driver = driverRepository.findById(id);
		if(driver.isPresent())
		{
			driver.get().setStatus(status);
			driverRepository.save(driver.get());
			
		}
		 return ResponseVO.create(HttpStatus.OK.value(), ResponseStatus.SUCCESS.name(),
				 driver.get().getDriverId() == null ? Messages.DRIVER_SUBMIT_SUCCESS
						: Messages.DRIVER_UPDATE_SUCCESS,
						getDriver( driver.get().getDriverId()));
	}

	@Override
	@Transactional(readOnly = true)
	public DriverDto getDriver(Long id) {
			Optional<Driver> driver = driverRepository.findById(id);
			if (driver.isPresent()) {
				return convertToVO(driver.get());
			}
			return null;
		}

	private DriverDto convertToVO(Driver driver) {
		DriverDto driverDto=new DriverDto();
		driverDto.setCabNumber(driver.getCabNumber());
		driverDto.setDriverId(driver.getDriverId());
		driverDto.setLicenseNumber(driver.getLicenseNumber());
		driverDto.setPhoneNumber(driver.getPhoneNumber());
		driverDto.setStatus(driver.getStatus());
		return driverDto;
	}

}
