package com.codirect.codiappapi.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codirect.codiappapi.model.Profile;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.security.UserDetailsImpl;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService  {

	@Autowired
	private UserService userService;
	
	public Function<Profile, SimpleGrantedAuthority> toGrantedAuthority = (profile) ->  new SimpleGrantedAuthority(profile.getRole().name());
	
	public UserDetailsImpl build(User user) {
		return UserDetailsImpl.builder()
				.withActive(user.isActive())
				.withRoles(buildRoles(user))
				.withEmail(user.getEmail())	
				.withId(user.getId())
				.withName(user.getName())
				.withPassword(user.getPassword())
				.build();
	}
	
	public UserDetails loadUserByEmail(String email) {
		return build(userService.findByEmail(email, "profiles"));
	}
		
	private List<GrantedAuthority> buildRoles(User user) {
		return user.getProfiles().stream().map(toGrantedAuthority).collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return build(userService.findByEmail(username, "profiles"));
	}
}
