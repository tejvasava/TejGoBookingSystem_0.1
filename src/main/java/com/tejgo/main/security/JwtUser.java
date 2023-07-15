package com.tejgo.main.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tejgo.main.core.User;

public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;

	public JwtUser(User user) {
	        name=user.getEmail();
	        password=user.getPassword();
	       
	        /*authorities= Arrays.stream(user.getRoles().split(","))
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList()); */
	        
	        authorities= Arrays.stream(user.getUserRole().getRoleName().split(","))
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
