package com.codirect.codiappapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codirect.codiappapi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select distinct coalesce(l.lead.id, c.lead.id) from Post p "
			+ "left join p.comments as c "
			+ "left join p.likes as l "
			+ "where p.account.id = :accountId "
			+ "and (cast(:fromPostDate as date) is null or p.postDate >= :fromPostDate)"
			+ "and (cast(:toPostDate as date) is null or p.postDate <= :toPostDate) "
			+ "and (l is not null or c is not null)")
	public List<Long> findLeadIdsByAccountIdAndPostPeriod(Long accountId, LocalDateTime fromPostDate, LocalDateTime toPostDate);
}
