package com.codirect.codiappapi.controller.privates;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.TagLeadAccountLeadDTO;
import com.codirect.codiappapi.dto.privates.TagLeadDTO;
import com.codirect.codiappapi.model.TagLead;
import com.codirect.codiappapi.model.TagLeadAccountLead;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.service.TagLeadService;
import com.codirect.codiappapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("private/tag-lead")
public class TagLeadController {

	@Autowired
	private TagLeadService service;
	
	@Autowired
	private UserService userService;	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<TagLeadDTO> listTagLeads() {
		User user = userService.getUserLogged();
		return service.listAllTags(user).stream().map(TagLeadService.toTagLeadDTO).collect(Collectors.toList());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TagLeadDTO createTagLead(@RequestBody Map<String, Object> params) {
		TagLeadDTO tagLeadDTO = new ObjectMapper().findAndRegisterModules().convertValue(params, TagLeadDTO.class);
		User user = userService.getUserLogged();
		TagLead tagLead = service.createTagLead(user, tagLeadDTO);
		return TagLeadService.toTagLeadDTO.apply(tagLead);
	}
	
	@PutMapping("/{tagLeadId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TagLeadDTO updateTagLead(@PathVariable Long tagLeadId, @RequestBody Map<String, Object> params) {
		TagLeadDTO tagLeadDTO = new ObjectMapper().findAndRegisterModules().convertValue(params, TagLeadDTO.class);
		TagLead tagLead = service.updateTagLead(tagLeadId, tagLeadDTO);
		return TagLeadService.toTagLeadDTO.apply(tagLead);
	}
	
	@DeleteMapping("/{tagLeadId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteTagLead(@PathVariable Long tagLeadId) {
		service.deleteTagLead(tagLeadId);
	}
	
	
	@PostMapping("/{tagLeadId}/account-lead/{accountLeadId}")
	@ResponseStatus(HttpStatus.CREATED)
	public TagLeadAccountLeadDTO addTagLeadAccountLead(@PathVariable Long tagLeadId, @PathVariable Long accountLeadId) {
		if (service.existsTagLeadAccountLead(tagLeadId, accountLeadId)) return null;
		TagLeadAccountLead tagLeadAccountLead = service.addTagLeadAccountLead(tagLeadId, accountLeadId);
		return TagLeadService.toTagLeadAccountLeadDTO.apply(tagLeadAccountLead);
	}
	
	@DeleteMapping("/{tagLeadId}/account-lead/{accountLeadId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteTagLeadAccountLead(@PathVariable Long tagLeadId, @PathVariable Long accountLeadId) {
		if (!service.existsTagLeadAccountLead(tagLeadId, accountLeadId)) return;
		service.deleteTagLeadAccountLead(tagLeadId, accountLeadId);
	}
	
	
}
