package com.codirect.codiappapi.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "lead")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_lead", sequenceName = "sq_lead")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Lead extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_lead")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private Long pk;

	private String accountName;
	
	private String fullName;

	private String biography;
	
	private String picUrlId;
	
	private String profilePicUrl;

	private String publicEmail;
	
	private String contactPhoneNumber;
	
	private String publicPhoneNumber;
	
	private String instagramLocation;
	
	@JsonBackReference("like")
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "lead")
	private Set<Like> likes;
	
	@JsonBackReference("comment")
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "lead")
	private Set<Comment> comments;
	
	Lead() {
		this.likes = new LinkedHashSet<>();
		this.comments = new LinkedHashSet<>();
	}
}
