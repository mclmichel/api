package com.codirect.codiappapi.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.codirect.codiappapi.dto.privates.AccountDTO;
import com.codirect.codiappapi.enums.StatusLoginInstagramEnum;
import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.properties.CodiappImportStory;
import com.codirect.codiappapi.util.CryptographyUtil;

@Service
public class SettingService {
	
	@Autowired
	private AccountService accountService;
		
	@Autowired
	private MessageSource message;
	
	@Autowired
	private CodiappImportStory codiappImportStory;

	public Account updatePasswordInstagram(AccountDTO accountDTO) {
		Account account = accountService.getAccountSelected();
		CryptographyUtil crypto = new CryptographyUtil(account.getSalt());

		if (!ObjectUtils.isEmpty(accountDTO.getPassword())) {
			account.setPassword(crypto.encrypt(accountDTO.getPassword()));
			accountService.save(account);
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl = codiappImportStory.getUrl() + "/accounts/statusPasswordAccount/";
			ResponseEntity<String> responseStatusRetornoLogin = restTemplate.getForEntity(fooResourceUrl + accountDTO.getId(), String.class);
			String statusRetornoLogin = responseStatusRetornoLogin.getBody();

			if (StatusLoginInstagramEnum.BAD_PASSWORD.getDescription().equals(statusRetornoLogin)) {
				forgetPassword(account);
				throw new CodiappException(message.getMessage("err.account-login-instagram-BAD_PASSWORD-not-found", new Object[] { } , Locale.getDefault()), HttpStatus.BAD_REQUEST);
			}

			if (StatusLoginInstagramEnum.TWO_FACTOR_AUTH.getDescription().equals(statusRetornoLogin)) {
				throw new CodiappException(message.getMessage("err.account-login-instagram-TWO_FACTOR_AUTH-not-found", new Object[] { statusRetornoLogin } , Locale.getDefault()), HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return account;
	}
	
	public void validateCodeTwoFactorAuth(String code) {
		Account account = accountService.getAccountSelected();
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = codiappImportStory.getUrl() + "/accounts/isCodeTwoFactorAuthValid/";
		ResponseEntity<Boolean> responseIsCodeTwoFactorAuthValid = restTemplate.getForEntity(fooResourceUrl + account.getId() + "/" + code, Boolean.class);
		if(!responseIsCodeTwoFactorAuthValid.getBody()) {
			throw new CodiappException(message.getMessage("err.account-login-instagram-TWO_FACTOR_AUTH-code-invalid-not-found", new Object[] { code } , Locale.getDefault()), HttpStatus.BAD_REQUEST);
		}
	}

	public void forgetPassword(Account account) {
		account = ObjectUtils.isEmpty(account) ? accountService.getAccountSelected() : account;
		account.setPassword(null);
		account.setClientCookie(null);
		account.setClientLogin(null);
		account.setClientReloginDate(null);
		accountService.save(account);
	}
}
