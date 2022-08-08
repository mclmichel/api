package com.codirect.codiappapi.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codirect.codiappapi.dto.privates.LeadDetailDTO;
import com.codirect.codiappapi.dto.privates.TagLeadAccountLeadDTO;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.model.Lead;
import com.codirect.codiappapi.repository.AccountLeadRepository;

@Service
public class LeadDetailService {
	
	@Autowired
	private AccountLeadService accountLeadService;
	
	@Autowired
	private AccountLeadRepository accountLeadRepository;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private RankingService rankingService;
	
	@Autowired
	private TagLeadService tagLeadService;
			
	Function<AccountLead, LeadDetailDTO> toLeadDetailDTO = (accountLead) -> {
		Lead lead = accountLead.getLead();
		List<TagLeadAccountLeadDTO> tagLeads = tagLeadService.listTagLeadsAccountLead(accountLead).stream()
				.map(TagLeadService.toTagLeadAccountLeadDTO).collect(Collectors.toList());
		return LeadDetailDTO.builder()
				.withCommentCount(accountLead.getCommentCount())
				.withLikeCount(accountLead.getLikeCount())
				.withStoryViewCount(accountLead.getStoryViewCount())
				.withFirstInteraction(accountLead.getFirstInteraction())
				.withLastInteraction(accountLead.getLastInteraction())
				.withFavorite(accountLead.isFavorite())
				.withNote(accountLead.getNote())
				.withRanking(rankingService.getRanking(accountLead))
				.withScore(accountLead.getScore())
				.withAccountName(lead.getAccountName())
				.withId(lead.getId())
				.withName(lead.getFullName())
				.withPicUrl(lead.getProfilePicUrl())
				.withBiography(lead.getBiography())
				.withTagLeadAccountLeads(tagLeads)
				.withAccountLeadId(accountLead.getId())
				.build();
	};
	
	public LeadDetailDTO getLeadDetail(Long leadId) {
		Account accountSelected = accountService.getAccountSelected();
		AccountLead accountLead = accountLeadService.findByAccountIdAndLeadId(accountSelected.getId(), leadId, "lead");
		return toLeadDetailDTO.apply(accountLead);
	}

	public void setFavorite(Long leadId, boolean isFavorite) {
		Account accountSelected = accountService.getAccountSelected();
		AccountLead accountLead = accountLeadService.findByAccountIdAndLeadId(accountSelected.getId(), leadId);
		accountLeadRepository.save(accountLead.toBuilder().withFavorite(isFavorite).build());
	}

}
