package com.codirect.codiappapi.controller.privates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.LeadDTO;
import com.codirect.codiappapi.service.RankingService;

@RestController
@RequestMapping("private/ranking")
public class RankingController {
	
	@Autowired
	private RankingService service;
	
	@GetMapping("/top-comment")
	@ResponseStatus(HttpStatus.OK)
	public List<LeadDTO> listTopComment() {
		return service.listTop(RankingService.ResourceTop.COMMENT, "lead");
	}
	
	@GetMapping("/top-like")
	@ResponseStatus(HttpStatus.OK)
	public List<LeadDTO> listTopLike() {
		return service.listTop(RankingService.ResourceTop.LIKE, "lead");
	}
	
	@GetMapping("/top-storyview")
	@ResponseStatus(HttpStatus.OK)
	public List<LeadDTO> listTopStoryView() {
		return service.listTop(RankingService.ResourceTop.STORY_VIEW, "lead");
	}
	
	@GetMapping("/top-general")
	@ResponseStatus(HttpStatus.OK)
	public List<LeadDTO> listTopGeneral() {
		return service.listTop(RankingService.ResourceTop.GENERAL, "lead");
	}
}
