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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tag_lead")
@Entity
@SequenceGenerator(initialValue = 10, allocationSize = 1, name = "gen_tag_lead", sequenceName = "sq_tag_lead")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TagLead extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_tag_lead")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "int8")
	private User user;

	@Column(nullable = false)
	private String name;
	
}
