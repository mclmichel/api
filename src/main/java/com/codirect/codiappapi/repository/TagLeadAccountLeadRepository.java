package com.codirect.codiappapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.TagLeadAccountLead;

@Repository
public interface TagLeadAccountLeadRepository extends JpaRepository<TagLeadAccountLead, Long> {

	public List<TagLeadAccountLead> findByAccountLeadId(Long accountLeadId);
	
	public List<TagLeadAccountLead> findByTagLeadId(Long tagLeadId);
	
	public TagLeadAccountLead findByAccountLeadIdAndTagLeadId(Long accountLeadId, Long tagLeadId);
	
	public boolean existsByAccountLeadIdAndTagLeadId(Long accountLeadId, Long tagLeadId);
}
