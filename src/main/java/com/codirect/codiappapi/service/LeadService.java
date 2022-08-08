package com.codirect.codiappapi.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codirect.codiappapi.dto.privates.LeadDTO;
import com.codirect.codiappapi.model.Lead;
import com.codirect.codiappapi.repository.LeadRepository;

@Service
public class LeadService {

	@Autowired
	private LeadRepository repository;
	
	public Function<Lead, LeadDTO> toLeadDTO = (lead) -> {
		return LeadDTO.builder()
				.withAccountName(lead.getAccountName())
				.withId(lead.getId())
				.withName(lead.getFullName())
				.withPicUrl(lead.getProfilePicUrl())
				.build();				
	};
	
	public String getProfilePicUrl(Long pk) {
		return repository.findProfilePicUrlByPk(pk).orElse(null);
	}

}
