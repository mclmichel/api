package com.codirect.codiappapi.dto.publics;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class UserDTO {

	private String token;
	private String type;
	private String code;
	private String name;
	private boolean active;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDTO() {
		this.type = "Bearer";
		this.authorities = new LinkedHashSet<>();
	}
}
