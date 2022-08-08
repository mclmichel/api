package com.codirect.codiappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

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
@Table(name = "profile")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_profile", sequenceName = "sq_profile")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class Profile extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_profile")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name = "name")
	private Profile.Role role;
	
	public static enum Role {
		BASIC, ADMIN;
	}
}
