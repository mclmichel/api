package com.codirect.codiappapi.service;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codirect.codiappapi.dto.privates.TagLeadAccountLeadDTO;
import com.codirect.codiappapi.dto.privates.TagLeadDTO;
import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.model.TagLead;
import com.codirect.codiappapi.model.TagLeadAccountLead;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.repository.TagLeadAccountLeadRepository;
import com.codirect.codiappapi.repository.TagLeadRepository;
import com.codirect.codiappapi.util.ObjectUtil;

@Service
public class TagLeadService {

	@Autowired
	private TagLeadRepository repository;
	
	@Autowired
	private TagLeadAccountLeadRepository tagLeadAccountLeadRepository;
	
	@Autowired
	private MessageSource message;
	
	public List<TagLead> listAllTags(User user) {
		return repository.findAll(user.getId());
	}
	
	public List<TagLeadAccountLead> listTagLeadsAccountLead(AccountLead accountLead) {
		return tagLeadAccountLeadRepository.findByAccountLeadId(accountLead.getId());
	}
	
	public TagLead createTagLead(User user, TagLeadDTO tagLeadDTO) {
		tagLeadDTO.setUserId(user.getId());
		TagLead tagLead = toTagLead.apply(tagLeadDTO);
		return repository.save(tagLead);
	}
	
	public TagLead updateTagLead(Long tagLeadId, TagLeadDTO tagLeadDTO) {
		ignoreGlobalTagLeads(tagLeadId);
		TagLead tagLead = repository.findById(tagLeadId).get();
		TagLead tagLeadNew = toTagLead.apply(tagLeadDTO);
		updateTagLeadFields(tagLeadNew, tagLead);
		return repository.save(tagLead);
	}
	
	public void deleteTagLead(Long tagLeadId) {
		ignoreGlobalTagLeads(tagLeadId);
		List<TagLeadAccountLead> tagLeadAccountLeads = tagLeadAccountLeadRepository.findByTagLeadId(tagLeadId);
		tagLeadAccountLeadRepository.deleteAll(tagLeadAccountLeads);
		TagLead tagLead = repository.findById(tagLeadId).get();
		repository.delete(tagLead);
	}
	
	public boolean existsTagLeadAccountLead(Long tagLeadId, Long accountLeadId) {
		return tagLeadAccountLeadRepository.existsByAccountLeadIdAndTagLeadId(accountLeadId, tagLeadId);
	}
	
	public TagLeadAccountLead addTagLeadAccountLead(Long tagLeadId, Long accountLeadId) {
		TagLead tagLead = repository.findById(tagLeadId).get();
		AccountLead accountLead = AccountLead.builder().withId(accountLeadId).build();
		TagLeadAccountLead tagLeadAccountLead = TagLeadAccountLead.builder().withAccountLead(accountLead).withTagLead(tagLead).build();
		return tagLeadAccountLeadRepository.save(tagLeadAccountLead);
	}
	
	public void deleteTagLeadAccountLead(Long tagLeadId, Long accountLeadId) {
		TagLeadAccountLead tagLeadAccountLead = tagLeadAccountLeadRepository.findByAccountLeadIdAndTagLeadId(accountLeadId, tagLeadId);
		tagLeadAccountLeadRepository.delete(tagLeadAccountLead);
	}
	
	public static Function<TagLeadAccountLead, TagLeadAccountLeadDTO> toTagLeadAccountLeadDTO = (tagLeadAccountLead) -> { 
		return TagLeadAccountLeadDTO.builder()
				.withId(tagLeadAccountLead.getId())
				.withTagLead(TagLeadService.toTagLeadDTO.apply(tagLeadAccountLead.getTagLead()))
				.withAccountLeadId(tagLeadAccountLead.getAccountLead().getId())
				.build();
	};
	
	public static Function<TagLead, TagLeadDTO> toTagLeadDTO = (tagLead) -> { 
		return TagLeadDTO.builder()
				.withId(tagLead.getId())
				.withName(tagLead.getName())
				.withUserId(ObjectUtil.isNull(tagLead.getUser()) ? null : tagLead.getUser().getId())
				.build();
	};
	
	public static Function<TagLeadDTO, TagLead> toTagLead = (tagLead) -> { 
		return TagLead.builder()
				.withId(tagLead.getId())
				.withName(tagLead.getName())
				.withUser(User.builder().withId(tagLead.getUserId()).build())
				.build();
	};
	
	private void ignoreGlobalTagLeads(Long tagLeadId) {
		if(tagLeadId < 10) throw new CodiappException(message.getMessage("err.forbidden", null, Locale.getDefault()), HttpStatus.FORBIDDEN);
	}
	
	private void updateTagLeadFields(TagLead from, TagLead to) {
		to.setName(from.getName());
	}
	
}
