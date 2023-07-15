package com.tejgo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.dto.UserDto;
import com.tejgo.main.service.UserService;

@RestController
@RequestMapping("/employee")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
	@PostMapping("/addEditEmployee")
	public  ResponseVO<UserDto>  addEditEmployee(@RequestBody UserDto userDto) {
		return userService.addEditEmployee(userDto);
	}
	
	@GetMapping("/listEmployee")
	public Page<UserDto> getAllEmployee(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return userService.findAllEmployee(pageNo, pageSize);
	}
	
	@GetMapping("/getEmployee")
	public UserDto getCustomerById(@RequestParam("id") Long id) {
		return userService.getEmployeeVOById(id);
	}
	
	@PostMapping("/registerCustomer")
	public  ResponseVO<UserDto>  addEditUser(@RequestBody UserDto userDto) {
		return userService.addEditCustomer(userDto);
	}
	
}
