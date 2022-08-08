package com.codirect.codiappapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codirect.codiappapi.service.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwtToken = getJwtToken(request);
			if (!ObjectUtils.isEmpty(jwtToken) && jwtProvider.validateJwtToken(jwtToken)) { 
				String email = jwtProvider.getEmail(jwtToken);
				UserDetails userDetails = userDetailsService.loadUserByEmail(email);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
	
	private String getJwtToken(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (ObjectUtils.isEmpty(authHeader)) return null;
		return authHeader.replace("Bearer ", "").replace("\"", "");
	}
}
