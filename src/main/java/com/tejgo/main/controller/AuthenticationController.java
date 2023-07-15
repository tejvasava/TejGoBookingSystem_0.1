package com.tejgo.main.controller;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tejgo.main.core.User;
import com.tejgo.main.dto.AuthRequest;
import com.tejgo.main.dto.JwtResponse;
import com.tejgo.main.security.JwtUserDetailsService;
import com.tejgo.main.security.JwtUtils;
import com.tejgo.main.service.UserService;


@RestController
public class AuthenticationController {

	@Autowired
	private JwtUtils jwtUtil;

	//@Autowired
	//private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UserService userService;
	

	@GetMapping("/")
	public String welComeUser() {
		return "Welcome to user !!";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest){
		String token = StringUtils.EMPTY;
		User user = userService.getUserByUserName(authRequest.getEmail());
		if (user != null) {
			authenticate(authRequest.getEmail(), authRequest.getPassword());
			final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(authRequest.getEmail());
			token = jwtUtil.generateToken(userDetails);
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	

	private void authenticate(String username, String password) throws BadCredentialsException ,DisabledException{
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}
}
