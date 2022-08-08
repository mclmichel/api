package com.codirect.codiappapi.dto.privates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TagLeadDTO {

	private Long id;
	private String name;
	private Long userId;
	
}
