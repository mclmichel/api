package com.codirect.codiappapi.controller.publics;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.publics.LoginDTO;
import com.codirect.codiappapi.security.JwtProvider;

@RestController
@RequestMapping("public/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;
		
	@PostMapping()
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Map<String, String> login(@Valid @RequestBody LoginDTO login) {
		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		Authentication authentication = authenticationManager.authenticate(userToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateJwtToken(authentication);
		LinkedHashMap<String, String> responseValue = new LinkedHashMap<>();
		responseValue.put("value", token);
		return responseValue;
	}
}
