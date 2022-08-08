package com.codirect.codiappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codirect.codiappapi.model.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story_lead_view")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_story_lead_view", sequenceName = "sq_story_lead_view")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class StoryLeadView extends BaseEntity {

	private static final long serialVersionUID = 5730967309478303288L;

	@Id
	@GeneratedValue(generator = "gen_story_lead_view")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "story_id", nullable = false)
	private Story story;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id", nullable = false)
	private Lead lead;
	
}
