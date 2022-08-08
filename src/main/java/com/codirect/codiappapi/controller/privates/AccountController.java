package com.codirect.codiappapi.controller.privates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.AccountDTO;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.service.AccountService;
import com.codirect.codiappapi.service.UserService;

@RestController
@RequestMapping("private/account")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@Autowired
	private UserService userService;
			
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<AccountDTO> listAccountsByActiveTrue() {
		User user = userService.getUserLogged();
		return service.listAccountByUserAndActive(user, true).stream()
				.map(service.toAccountDTO).collect(Collectors.toList());
	}
	
	@GetMapping("/selected")
	@ResponseStatus(HttpStatus.OK)
	public AccountDTO getAccountSelected() {
		return service.toAccountDTO.apply(service.getAccountSelected());
	}
	
	@PutMapping("/{accountId}/selected")
	@ResponseStatus(HttpStatus.OK)
	public void setAccountSelected(@PathVariable Long accountId) {
		User user = userService.getUserLogged();
		service.setAccountSelected(user, accountId);
	}
}
