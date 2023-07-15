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
import com.tejgo.main.dto.UserRoleDto;
import com.tejgo.main.service.UserRoleService;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public  ResponseVO<Void>  addEditRole(@RequestBody UserRoleDto role) {
		return userRoleService.addEditRole(role);
	}
	
	
	@GetMapping("/useRole")
	public UserRoleDto getUserVoByid(@RequestParam("id") Long id) {
		return userRoleService.getUserVoByid(id);
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
	@GetMapping("/listRoles")
	public Page<UserRoleDto> getAllUsers(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		return userRoleService.findAllUsersRolesList(pageNo, pageSize);
	}
	
	
}
