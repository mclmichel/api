package com.codirect.codiappapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

	@Query("select distinct sv.lead.id from Story s "
			+ "join s.storyViews as sv "
			+ "where s.account.id = :accountId "
			+ "and (cast(:fromStoryDate as date) is null or s.takenDate >= :fromStoryDate)"
			+ "and (cast(:toStoryDate as date) is null or s.takenDate <= :toStoryDate)")
	public List<Long> findLeadIdsByAccountIdAndStoryPeriod(Long accountId, LocalDateTime fromStoryDate, LocalDateTime toStoryDate);
}
