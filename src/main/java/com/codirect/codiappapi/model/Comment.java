package com.codirect.codiappapi.model;

import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@Table(name = "comment")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_comment", sequenceName = "sq_comment")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Comment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_comment")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String pk;

	@Column(length = 2000)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id", nullable = false)
	private Lead lead;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	private LocalDateTime date;
}
