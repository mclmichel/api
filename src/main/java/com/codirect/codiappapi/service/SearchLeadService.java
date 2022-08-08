package com.codirect.codiappapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.dto.privates.LeadDetailDTO;
import com.codirect.codiappapi.dto.privates.SearchLeadCriteriaDTO;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.repository.AccountLeadRepository;
import com.codirect.codiappapi.repository.PostRepository;
import com.codirect.codiappapi.repository.StoryRepository;
import com.codirect.codiappapi.util.ObjectUtil;

@Service
public class SearchLeadService {

	@Autowired
	private AccountLeadRepository accountLeadRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private LeadDetailService leadDetailService;
	
	
	public Page<LeadDetailDTO> searchLead(SearchLeadCriteriaDTO criteria, Pageable pageable) {
		configCriteria(criteria);
		Page<AccountLead> accountLeads = accountLeadRepository.searchLead(criteria, pageable);
		return accountLeads.map(leadDetailService.toLeadDetailDTO);
	}
	
	private void configCriteria(SearchLeadCriteriaDTO criteria) {
		List<Long> tagLeadIds = ObjectUtil.toList(criteria.getTagLeads(), ",").stream().map(value -> ObjectUtil.toLong(value)).collect(Collectors.toList());
		criteria.setTagLeadIds(tagLeadIds);
		if (ObjectUtils.isEmpty(criteria.getFromPostDate()) && ObjectUtils.isEmpty(criteria.getToPostDate())) return;
		Account account = accountService.getAccountSelected();
		List<Long> postLeadIds = postRepository.findLeadIdsByAccountIdAndPostPeriod(account.getId(), criteria.getFromPostDate(), criteria.getToPostDate());
		List<Long> storyLeadIds = storyRepository.findLeadIdsByAccountIdAndStoryPeriod(account.getId(), criteria.getFromPostDate(), criteria.getToPostDate());
		postLeadIds.addAll(storyLeadIds);
		criteria.setLeadIds(postLeadIds);
	}

}
