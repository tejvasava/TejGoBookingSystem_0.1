package com.tejgo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejgo.main.dto.CabDto;
import com.tejgo.main.dto.ResponseVO;
import com.tejgo.main.service.CabService;

@RestController
@RequestMapping("/cab")
public class CabController {

	@Autowired
	private CabService cabService;
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
	public  ResponseVO<CabDto>  addEditDriver(@RequestBody CabDto cabDto) {
		return cabService.addEditCab(cabDto);
	}
}
