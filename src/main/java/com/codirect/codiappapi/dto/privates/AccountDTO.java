package com.codirect.codiappapi.dto.privates;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class AccountDTO {

	private Long id;
	private Long pk;
	private String name;
	private String profilePicUrl;
	private boolean hasPassword;

	@JsonBackReference("password")
	private String password;
		
}
