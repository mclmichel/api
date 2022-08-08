package com.codirect.codiappapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.TagLead;

@Repository
public interface TagLeadRepository extends JpaRepository<TagLead, Long> {
	
	@Query("select tl from TagLead tl where (tl.user is null or tl.user.id = :userId) order by tl.name")
	public List<TagLead> findAll(Long userId);

}
