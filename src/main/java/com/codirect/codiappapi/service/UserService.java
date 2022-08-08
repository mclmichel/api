package com.codirect.codiappapi.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.exception.CodiappException;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.User;
import com.codirect.codiappapi.model.UserSetting;
import com.codirect.codiappapi.repository.UserRepository;
import com.codirect.codiappapi.security.UserDetailsImpl;
import com.codirect.codiappapi.util.ObjectUtil;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MessageSource message;
	
	public User findByEmail(String email, String ...attributePaths) {
		EntityGraph entityGraph = ObjectUtils.isEmpty(attributePaths) ? null : EntityGraphUtils.fromAttributePaths(attributePaths);
		return repository.findByEmail(email, entityGraph)
				.orElseThrow(() -> new CodiappException(message.getMessage("err.user-not-found", new Object[] { email } , Locale.getDefault()), HttpStatus.BAD_REQUEST));
	}
	
	public User findById(Long userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new CodiappException(message.getMessage("err.user-not-found", new Object[] { userId }, Locale.getDefault()), HttpStatus.BAD_REQUEST));
	}
	
	public User getUserLogged() {
		SecurityContext context= SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (ObjectUtils.isEmpty(auth) || ObjectUtils.isEmpty(auth.getPrincipal())) throw new RuntimeException(message.getMessage("err.token-invalid", null, Locale.getDefault()));
		UserDetailsImpl userDetail = (UserDetailsImpl) auth.getPrincipal();
		return findById(userDetail.getId());
	}
	
	public Account saveAccountSelected(User user, Account account) {
		UserSetting setting = ObjectUtil.isNull(user.getSetting()) ? new UserSetting() : user.getSetting();
		setting.setAccountSelected(account);
		user.setSetting(setting);
		repository.save(user);
		return account;
	}
}
