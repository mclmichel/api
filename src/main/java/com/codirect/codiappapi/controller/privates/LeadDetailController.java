package com.codirect.codiappapi.controller.privates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.LeadDetailDTO;
import com.codirect.codiappapi.service.LeadDetailService;

@RestController
@RequestMapping("private/lead")
public class LeadDetailController {

	@Autowired
	private LeadDetailService service;
	
	@GetMapping("/{leadId}")
	@ResponseStatus(HttpStatus.OK)
	public LeadDetailDTO getLeadDetail(@PathVariable Long leadId) {
		return service.getLeadDetail(leadId);
	}
	
	@PutMapping("/{leadId}/favorite/{isFavorite}")
	public void setFavorite(@PathVariable Long leadId, @PathVariable boolean isFavorite) {
		service.setFavorite(leadId, isFavorite);
	}
}
