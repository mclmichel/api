package com.codirect.codiappapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	public List<Account> findByUserIdAndActive(Long userId, boolean active);
	
	public Optional<Account> findTopByUserIdAndActive(Long userId, boolean active);
	
	public Optional<Account> findByUserId(Long userId);
}
