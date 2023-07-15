package com.tejgo.main.dto;
import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

}
