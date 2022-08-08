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
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@AllArgsConstructor
@Table(name = "post")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_post", sequenceName = "sq_post")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Post extends BaseEntity  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_post")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private Long pk;
	
	@Column(nullable = false)
	private LocalDateTime postDate;
	
	private String feedPhotoId;

	private Integer commentCount;
	
	private Integer likeCount;
	
	private Integer viewCount;
	
	@Column(length = 2500)
	private String description;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@JsonBackReference("likes")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Like> likes;
	
	@JsonBackReference("comments")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Comment> comments;
	
	public Post() {
		this.commentCount = 0;
		this.likeCount = 0;
		this.viewCount = 0;
		this.likes = new LinkedHashSet<>();
		this.comments = new LinkedHashSet<>();
	}
}
