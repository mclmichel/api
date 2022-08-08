package com.codirect.codiappapi.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.repository.AccountLeadRepository;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;

@Service
public class AccountLeadService {

	@Autowired
	private AccountLeadRepository repository;
	
	@Autowired
	private MessageSource message;
	
	public AccountLead findByAccountIdAndLeadId(Long accountId, Long leadId, String ...attributePaths) {
		EntityGraph entityGraph = ObjectUtils.isEmpty(attributePaths) ? null : EntityGraphUtils.fromAttributePaths(attributePaths);
		return repository.findByAccountIdAndLeadId(accountId, leadId, entityGraph)
				.orElseThrow(() -> new CodiappException(message.getMessage("err.lead-not-found", null, Locale.getDefault()), HttpStatus.NOT_FOUND));
	}
}
