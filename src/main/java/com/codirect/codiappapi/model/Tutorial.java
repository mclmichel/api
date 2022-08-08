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
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@AllArgsConstructor
@Table(name = "tutorial")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_tutorial", sequenceName = "sq_tutorial")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Tutorial extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_tutorial")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;	
	
	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean showConfigAccount;
	
	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean showFirstLogin;
	
	public Tutorial() {
		this.showConfigAccount = true;
		this.showFirstLogin = true;
	}
}
