package com.codirect.codiappapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.repository.criteria.AccountLeadCriteria;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;

@Repository
public interface AccountLeadRepository extends JpaRepository<AccountLead, Long>, AccountLeadCriteria {
	
	public static final String QUERY_RAKING = "select al from AccountLead al "
			+ "where al.account.id = :accountId "
			+ "and al.lead.pk != :leadPk "
			+ "order by ";

	@Query(QUERY_RAKING + "commentCount desc")
	public List<AccountLead> listTopComment(Long accountId, Long leadPk, Pageable pageable, EntityGraph entityGraph);
	
	@Query(QUERY_RAKING + "likeCount desc")
	public List<AccountLead> listTopLike(Long accountId, Long leadPk, Pageable pageable, EntityGraph entityGraph);
	
	@Query(QUERY_RAKING + "storyViewCount desc")
	public List<AccountLead> listTopStoryView(Long accountId, Long leadPk, Pageable pageable, EntityGraph entityGraph);
	
	@Query(QUERY_RAKING + "score desc")
	public List<AccountLead> listTopGeneral(Long accountId, Long leadPk, Pageable pageable, EntityGraph entityGraph);
	
	public Optional<AccountLead> findByAccountIdAndLeadId(Long accountId, Long leadId, EntityGraph entityGraph);

}
