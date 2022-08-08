package com.codirect.codiappapi.repository.criteria;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import com.codirect.codiappapi.dto.privates.SearchLeadCriteriaDTO;
import com.codirect.codiappapi.model.Account;
import com.codirect.codiappapi.model.AccountLead;
import com.codirect.codiappapi.model.Lead;
import com.codirect.codiappapi.model.TagLeadAccountLead;
import com.codirect.codiappapi.service.AccountService;

public class AccountLeadCriteriaImpl implements AccountLeadCriteria {

	@Autowired
	private AccountService accountService;
	
	@PersistenceContext
	private EntityManager em;
		
	private Root<AccountLead> fromAccountLead;
	private Join<AccountLead, Lead> joinLead;
	private Join<AccountLead, TagLeadAccountLead> joinTagLeadAccountLead;
	
	@Override
	public Page<AccountLead> searchLead(SearchLeadCriteriaDTO criteria, Pageable pageable) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<AccountLead> query = builder.createQuery(AccountLead.class);
		
		fromAccountLead = query.from(AccountLead.class);
		joinLead = fromAccountLead.join("lead");
		
		query.select(fromAccountLead).distinct(true)
			.where(searchPredicates(builder, criteria, query))
			.orderBy(buildOrder(pageable.getSort()));
		
		int firstItem = pageable.getPageNumber() * pageable.getPageSize();
		List<AccountLead> result = em.createQuery(query)
				.setFirstResult(firstItem)
				.setHint("javax.persistence.fetchgraph", entityGraph())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		
		return new PageImpl<AccountLead>(result, pageable, countTotalResult(criteria));
	}
	
	private List<Order> buildOrder(Sort sort) {
		List<Order> orders = new LinkedList<>();
		if (sort.isEmpty()) return orders;
		sort.forEach(order -> orders.add(new OrderImpl(buildExpression(order.getProperty()), order.isAscending())));
		return orders;
	}
	
	private Expression<?> buildExpression(String field) {
		String[] fieldSplit = field.split("\\.");
		if (fieldSplit[0].equals("lead")) return joinLead.get(fieldSplit[1]);
		else return fromAccountLead.get(fieldSplit[0]);
	}
	
	private EntityGraph<AccountLead> entityGraph() {
		EntityGraph<AccountLead> entityGraph = em.createEntityGraph(AccountLead.class);
		entityGraph.addAttributeNodes("lead");
		return entityGraph;
	}

	private Long countTotalResult(SearchLeadCriteriaDTO searchLeadCriteria) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		fromAccountLead = query.from(AccountLead.class);
		joinLead = fromAccountLead.join("lead");
		
		query.select(builder.countDistinct(fromAccountLead.get("id"))).where(searchPredicates(builder, searchLeadCriteria, query));
		return em.createQuery(query).getSingleResult();
	}
	
	private Predicate[] searchPredicates(CriteriaBuilder builder, SearchLeadCriteriaDTO criteria, CriteriaQuery<?> query) {
		List<Predicate> predicates = new LinkedList<>();
		predicateAccountSelected(predicates, builder, criteria);
		predicateWordSearch(predicates, builder, criteria);
		predicateFirstInteraction(predicates, builder, criteria);
		predicateLastInteraction(predicates, builder, criteria);
		predicatePostPeriod(predicates, builder, criteria);
		predicateTagLeadIds(predicates, builder, criteria, query);
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void predicateTagLeadIds(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria, CriteriaQuery<?> query) {
		if (ObjectUtils.isEmpty(criteria.getTagLeadIds())) return;
		joinTagLeadAccountLead = fromAccountLead.join("tagLeadAccountLeads", criteria.getTagLeadsOption().equals("only") ? JoinType.INNER : JoinType.LEFT);
		if (criteria.getTagLeadIds().size() > 1) {
			moreThanOneTagLeadIds(predicates, builder, criteria, query);
		} else {
			onlyOneTagLeadId(predicates, builder, criteria, query);
		}
	}
	
	private void moreThanOneTagLeadIds(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria, CriteriaQuery<?> query) {
		if (criteria.getTagLeadsOption().equals("only")) {
			predicates.add(joinTagLeadAccountLead.get("tagLead").get("id").in(criteria.getTagLeadIds()));
			return;
		}
		Subquery<Integer> subqueryTagLead = query.subquery(Integer.class);
		Root<TagLeadAccountLead> fromTagLeadAccountLead = subqueryTagLead.from(TagLeadAccountLead.class);
		subqueryTagLead.select(builder.literal(1)).where(builder.and(
				fromTagLeadAccountLead.get("tagLead").get("id").in(criteria.getTagLeadIds()),
				builder.equal(fromAccountLead.get("id"), fromTagLeadAccountLead.get("accountLead").get("id"))));
		predicates.add(builder.or(
				builder.isNull(joinTagLeadAccountLead),
				builder.not(builder.exists(subqueryTagLead))));
	}
	
	
	
	private void onlyOneTagLeadId(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria, CriteriaQuery<?> query) {
		Long tagLeadId = criteria.getTagLeadIds().stream().findFirst().get();
		if (criteria.getTagLeadsOption().equals("only")) {
			predicates.add(builder.equal(joinTagLeadAccountLead.get("tagLead").get("id"), tagLeadId));
			return;
		}
		Subquery<Integer> subqueryTagLead = query.subquery(Integer.class);
		Root<TagLeadAccountLead> fromTagLeadAccountLead = subqueryTagLead.from(TagLeadAccountLead.class);
		subqueryTagLead.select(builder.literal(1)).where(builder.and(
				builder.equal(fromTagLeadAccountLead.get("tagLead").get("id"), tagLeadId),
				builder.equal(fromAccountLead.get("id"), fromTagLeadAccountLead.get("accountLead").get("id"))));
		predicates.add(builder.or(
				builder.isNull(joinTagLeadAccountLead),
				builder.notEqual(joinTagLeadAccountLead.get("tagLead").get("id"), tagLeadId)));
	}

	private void predicatePostPeriod(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria) {
		if (!(ObjectUtils.isEmpty(criteria.getFromPostDate()) && ObjectUtils.isEmpty(criteria.getToPostDate())))
			predicates.add(joinLead.get("id").in(criteria.getLeadIds()));		
	}

	private void predicateAccountSelected(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria) {
		Account account = accountService.getAccountSelected();
		predicates.add(builder.and(builder.equal(fromAccountLead.get("account").get("id"), account.getId()),
								builder.notEqual(joinLead.get("pk"), account.getPk())));
	}
	
	private void predicateWordSearch(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria) {
		if (ObjectUtils.isEmpty(criteria.getWord())) return;
		predicates.add(builder.or(builder.like(joinLead.get("fullName"), "%" + criteria.getWord() + "%"),
						  builder.like(joinLead.get("accountName"), "%" + criteria.getWord() + "%"),
						  builder.like(joinLead.get("biography"), "%" + criteria.getWord() + "%"),
						  builder.like(joinLead.get("publicEmail"), "%" + criteria.getWord() + "%"),
						  builder.like(joinLead.get("contactPhoneNumber"), "%" + criteria.getWord() + "%"),
						  builder.like(fromAccountLead.get("note"), "%" + criteria.getWord() + "%")));
	}
	
	private void predicateFirstInteraction(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria) {
		if (!ObjectUtils.isEmpty(criteria.getFromFirstInteractionDate()))
			predicates.add(builder.greaterThanOrEqualTo(fromAccountLead.get("firstInteraction"), criteria.getFromFirstInteractionDate()));
		if (!ObjectUtils.isEmpty(criteria.getToFirstInteractionDate()))
			predicates.add(builder.lessThanOrEqualTo(fromAccountLead.get("firstInteraction"), criteria.getToFirstInteractionDate()));
	}
	
	private void predicateLastInteraction(List<Predicate> predicates, CriteriaBuilder builder, SearchLeadCriteriaDTO criteria) {
		if (!ObjectUtils.isEmpty(criteria.getFromLastInteractionDate()))
			predicates.add(builder.greaterThanOrEqualTo(fromAccountLead.get("lastInteraction"), criteria.getFromLastInteractionDate()));
		if (!ObjectUtils.isEmpty(criteria.getToLastInteractionDate()))
			predicates.add(builder.lessThanOrEqualTo(fromAccountLead.get("lastInteraction"), criteria.getToLastInteractionDate()));
	}

}
