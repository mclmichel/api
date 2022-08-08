package com.codirect.codiappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codirect.codiappapi.model.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@Table(name = "account_setting")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_account_setting", sequenceName = "sq_account_setting")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class AccountSetting extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_account_setting")
	@Column(name = "id", nullable = false)
	private Long id;
	
}
