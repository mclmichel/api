package com.codirect.codiappapi.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "account")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_account", sequenceName = "sq_account")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Account  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_account")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(unique = true, nullable = false)
	private Long pk;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
	@JsonBackReference("password")
	private String password;
	
	@Column(columnDefinition = "boolean default true")
	private boolean active;
	
	@Column(nullable = false, columnDefinition = "varchar(24) default substring(md5(random()::text), 0, 25)")
	private String salt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne
	@JoinColumn(name = "account_setting_id")
	private AccountSetting setting;
	
	@JsonBackReference("clientLogin")
	private byte[] clientLogin;
	
	@JsonBackReference("clientCookie")
	private byte[] clientCookie;
	
	private LocalDateTime clientReloginDate;
	
	@OneToMany(mappedBy = "account")
	@JsonBackReference("posts")
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "account")
	@JsonBackReference("stories")
	private Set<Story> stories;
	
	@OneToMany(mappedBy = "account")
	@JsonBackReference("accountLeads")
	private Set<AccountLead> accountLeads;
	
	public Account() {
		this.active = true;
		this.posts = new LinkedHashSet<>();
		this.stories = new LinkedHashSet<>();
		this.accountLeads = new LinkedHashSet<>();
	}
}
