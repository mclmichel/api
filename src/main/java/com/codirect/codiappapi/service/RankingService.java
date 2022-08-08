package com.codirect.codiappapi.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.dto.privates.LeadDTO;
import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.repository.AccountLeadRepository;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;

@Service
public class RankingService {
	
	public static final long DAYS_TO_CONSIDER_NEW = 7L;
	
	public enum ResourceTop {
		COMMENT, LIKE, STORY_VIEW, GENERAL;
	}
	
	@Autowired
	private AccountLeadRepository accountLeadRepository;
	
	@Autowired
	private MessageSource message;
	
	@Autowired
	private LeadService leadService;
	
	@Autowired
	private AccountService accountService;
	
	public List<LeadDTO> listTop(ResourceTop resource, String ...attributePaths) {
		Account account = accountService.getAccountSelected();
		List<AccountLead> accountLeads = buildResource(resource, account, attributePaths);
		if (accountLeads.isEmpty()) throw new CodiappException(message.getMessage("err.user-no-leads", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
		return accountLeads.stream().map(al -> leadService.toLeadDTO.apply(al.getLead()).toBuilder()
					.withFavorite(al.isFavorite())
					.withRanking(getRanking(al)).build()
		).collect(Collectors.toList());
	}
	
	private List<AccountLead> buildResource(ResourceTop resource, Account account, String ...attributePaths) {
		EntityGraph entityGraph = ObjectUtils.isEmpty(attributePaths) ? null : EntityGraphUtils.fromAttributePaths(attributePaths);
		switch (resource) {
			case COMMENT: return accountLeadRepository.listTopComment(account.getId(), account.getPk(), PageRequest.of(0,10), entityGraph);
			case LIKE: return accountLeadRepository.listTopLike(account.getId(), account.getPk(), PageRequest.of(0,10), entityGraph);
			case STORY_VIEW: return accountLeadRepository.listTopStoryView(account.getId(), account.getPk(), PageRequest.of(0,10), entityGraph);
			case GENERAL: return accountLeadRepository.listTopGeneral(account.getId(), account.getPk(), PageRequest.of(0,10), entityGraph);
			default: return new LinkedList<>();
		}
	}
	
	public LeadDTO.RankingType getRanking(AccountLead accountLead) {
		Integer score = accountLead.getScore();
		boolean isNew = LocalDateTime.now().minusDays(DAYS_TO_CONSIDER_NEW).isBefore(accountLead.getFirstInteraction());
		if (isNew) return LeadDTO.RankingType.NEW;
		if (ObjectUtils.isEmpty(score)) return LeadDTO.RankingType.EMPTY;
		if (score < 250) return LeadDTO.RankingType.BRONZE;
		if (score < 500) return LeadDTO.RankingType.SILVER;
		if (score < 750) return LeadDTO.RankingType.GOLD;
		return LeadDTO.RankingType.DIAMOND;
	}

}
