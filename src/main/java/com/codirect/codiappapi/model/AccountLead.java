package com.codirect.codiappapi.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codirect.codiappapi.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@AllArgsConstructor
@Table(name = "account_lead")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_account_lead", sequenceName = "sq_account_lead")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class AccountLead extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_account_lead")
	@Column(name = "id", nullable = false)
	private Long id;
		
	@Column(nullable = false, columnDefinition = "INT default 0")
	private Integer commentCount;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private Integer likeCount;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private Integer storyViewCount;
	
	@Column(columnDefinition = "boolean default false")
	private boolean favorite;
	
	@Column(nullable = false, columnDefinition = "INT default 0")
	private Integer score;
	
	private LocalDateTime firstInteraction;
	
	private LocalDateTime lastInteraction;
	
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id", nullable = false)
	private Lead lead;
	
	@OneToMany(mappedBy = "accountLead")
	@JsonBackReference("tagLeadAccountLeads")
	private Set<TagLeadAccountLead> tagLeadAccountLeads;
		
	public AccountLead() {
		this.commentCount = 0;
		this.likeCount = 0;
		this.storyViewCount = 0;
	}

}
