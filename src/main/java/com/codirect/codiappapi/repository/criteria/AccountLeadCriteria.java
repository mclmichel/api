package com.codirect.codiappapi.repository.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codirect.codiappapi.dto.privates.SearchLeadCriteriaDTO;
import com.codirect.codiappapi.model.AccountLead;

public interface AccountLeadCriteria {

	Page<AccountLead> searchLead(SearchLeadCriteriaDTO searchLeadCriteria, Pageable pageable);
}
