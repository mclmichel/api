package com.codirect.codiappapi.controller.privates;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.LeadDetailDTO;
import com.codirect.codiappapi.dto.privates.SearchLeadCriteriaDTO;
import com.codirect.codiappapi.service.SearchLeadService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("private/search-lead")
public class SearchLeadController {

	@Autowired
	private SearchLeadService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<LeadDetailDTO> searchLead(@RequestParam Map<String, Object> params, Pageable pageable) {
		SearchLeadCriteriaDTO searchLeadCriteria = new ObjectMapper().findAndRegisterModules().convertValue(params, SearchLeadCriteriaDTO.class);
		return service.searchLead(searchLeadCriteria, pageable);
	}
}
