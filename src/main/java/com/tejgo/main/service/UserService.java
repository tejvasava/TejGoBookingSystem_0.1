package com.tejgo.main.service;

import org.springframework.data.domain.Page;

import com.tejgo.main.core.User;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.UserDto;

public interface UserService {

	ResponseVO<UserDto> addEditEmployee(UserDto employeeDto);

	Page<UserDto> findAllEmployee(int pageNo, int pageSize);

	UserDto getEmployeeVOById(Long id);

	User getUserByUserName(String email);

	ResponseVO<UserDto> addEditCustomer(UserDto userDto); 

}
