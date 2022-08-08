package com.codirect.codiappapi.dto.privates;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@EqualsAndHashCode(callSuper=false)
public class LeadDetailDTO extends LeadDTO {

	private Integer commentCount;
	private Integer likeCount;
	private Integer storyViewCount;
	private Integer score;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime firstInteraction;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastInteraction;
	private String note;
	private String biography;
	private List<TagLeadAccountLeadDTO> tagLeadAccountLeads;
	
}
