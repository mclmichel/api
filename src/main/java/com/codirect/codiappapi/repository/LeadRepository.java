package com.codirect.codiappapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

	@Query("select l.profilePicUrl from Lead l where l.pk = :pk")
	public Optional<String> findProfilePicUrlByPk(Long pk);

}
