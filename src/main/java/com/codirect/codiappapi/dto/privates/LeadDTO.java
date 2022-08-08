package com.codirect.codiappapi.dto.privates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class LeadDTO {
	
	public static enum RankingType {
		DIAMOND, GOLD, SILVER, BRONZE, NEW, EMPTY;
	}

	private Long id;
	private Long accountLeadId;
	private String accountName;
	private String name;
	private String picUrl;
	private boolean favorite;
	private RankingType ranking;
	
	public LeadDTO() {
		this.ranking = RankingType.EMPTY;
	}
}
