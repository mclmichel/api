package com.codirect.codiappapi.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@AllArgsConstructor
@Table(name = "`user`")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_user", sequenceName = "sq_user")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class User extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_user")
	@Column(name = "id", nullable = false)
	private Long id;
	
	private String email;
	
	@JsonBackReference
	private String password;
	
	private String name;
	
	@Column(columnDefinition = "varchar(7)")
	private String validationCode;
	
	@Column(columnDefinition = "boolean default false")
	private boolean active;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_setting_id")
	private UserSetting setting;
	
	@ManyToMany
	@JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
	private Set<Profile> profiles;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	@JsonBackReference("accounts")
	private Set<Account> accounts;
	
	public User() {
		this.profiles = new LinkedHashSet<>();
		this.accounts = new LinkedHashSet<>();
	}

}
