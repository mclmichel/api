package com.codirect.codiappapi.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@AllArgsConstructor
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@Table(name = "story")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_story", sequenceName = "sq_story")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Story extends BaseEntity {

	private static final long serialVersionUID = 2589329979688965404L;

	@Id
	@GeneratedValue(generator = "gen_story")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private Long pk;
	
	@Column(nullable = false)
	private LocalDateTime takenDate;
			
	private Integer viewersCount;
	
	private String 	imageUrl;
	
	@Column(length = 2500)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "story")
	@JsonBackReference("storyViews")
	private Set<StoryLeadView> storyViews;
	
	public Story() {
		this.viewersCount = 0;
		this.storyViews = new LinkedHashSet<>();
	}
}
