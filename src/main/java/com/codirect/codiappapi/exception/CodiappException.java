package com.codirect.codiappapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CodiappException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;

	public CodiappException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
