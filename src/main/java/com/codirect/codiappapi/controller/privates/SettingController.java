package com.codirect.codiappapi.controller.privates;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codirect.codiappapi.dto.privates.AccountDTO;
import com.codirect.codiappapi.service.AccountService;
import com.codirect.codiappapi.service.SettingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("private/setting")
public class SettingController {
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private AccountService accountService;

	@PutMapping("/account/update-password-instagram")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public AccountDTO updatePasswordInstagram(@RequestBody Map<String, Object> params) {
		AccountDTO accountDTO = new ObjectMapper().findAndRegisterModules().convertValue(params, AccountDTO.class);
		return accountService.toAccountDTO.apply(settingService.updatePasswordInstagram(accountDTO));
	}
	
	@PostMapping("/account/validate-code-two-factor-auth")
	@ResponseStatus(HttpStatus.OK)
	public void validateCodeTwoFactorAuth(@RequestParam String code) {
		settingService.validateCodeTwoFactorAuth(code);
	}
	
	@PutMapping("/account/forget-password")
	@ResponseStatus(HttpStatus.OK)
	public void forgetPassword() {
		settingService.forgetPassword(null);
	}
}
