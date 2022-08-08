package com.codirect.codiappapi.dto.publics;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class LoginDTO {

	@NotBlank
	@Size(max = 255)
	@Email
	private String username;
	
	@NotBlank
	@Size(min = 3, max = 40)
	@JsonBackReference("password")
	private String password;
}
