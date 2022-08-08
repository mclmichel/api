package com.codirect.codiappapi.service;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.dto.privates.AccountDTO;
import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.model.UserSetting;
import com.codirect.codiappapi.repository.AccountRepository;
import com.codirect.codiappapi.repository.UserRepository;
import com.codirect.codiappapi.util.ObjectUtil;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LeadService leadService;
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private MessageSource message;
	
	public void setAccountSelected(User user, Long accountId) {
		Account account = getAccountById(accountId);
		UserSetting setting = ObjectUtils.isEmpty(user.getSetting()) ? UserSetting.builder().withAccountSelected(account).build() : user.getSetting(); 
		if (account.getId().equals(setting.getAccountSelected().getId())) return;
		user.getSetting().setAccountSelected(account);
		userRepository.save(user);
	}
	
	public Account getAccountSelected() {
		User user = userService.getUserLogged();
		if (!ObjectUtil.isNull(user) 
				&& !ObjectUtil.isNull(user.getSetting())
				&& !ObjectUtil.isNull(user.getSetting().getAccountSelected())) {
			return user.getSetting().getAccountSelected();
		}
		Account firstAccount = findFirstAccount(user, true);
		return userService.saveAccountSelected(user, firstAccount);
	}
	
	public Account getAccountById(Long accountId) {
		return repository.findById(accountId)
				.orElseThrow(() -> new CodiappException(message.getMessage("err.user-no-accounts", null, Locale.getDefault()), HttpStatus.BAD_REQUEST));
	}
	
	public Function<Account, AccountDTO> toAccountDTO = (account) -> { 
		return AccountDTO.builder()
				.withId(account.getId())
				.withPk(account.getPk())
				.withName(account.getName())
				.withHasPassword(!ObjectUtils.isEmpty(account.getPassword()))
				.withProfilePicUrl(leadService.getProfilePicUrl(account.getPk()))
				.build();
	};
	
	public List<Account> listAccountByUserAndActive(User user, boolean active) {
		List<Account> accounts = this.repository.findByUserIdAndActive(user.getId(), active);
		if (accounts.isEmpty()) throw new CodiappException(message.getMessage("err.user-no-accounts", null, Locale.getDefault()), HttpStatus.BAD_REQUEST);
		return accounts;
	}
	
	public Account findFirstAccount(User user, boolean active) {
		return repository.findTopByUserIdAndActive(user.getId(), active)
				.orElseThrow(() -> new CodiappException(message.getMessage("err.user-no-accounts", null, Locale.getDefault()), HttpStatus.BAD_REQUEST));
	}


	public Account findById(Long id) {
		Account account = repository.findById(id).orElse(null);
		if (ObjectUtils.isEmpty(account)) {
			new CodiappException(message.getMessage("err.account-not-found", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
		}
		return account;
	}
	
	public Account save(Account account) {
		return repository.save(account);
	}
}
