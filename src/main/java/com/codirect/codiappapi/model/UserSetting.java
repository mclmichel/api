package com.codirect.codiappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "user_setting")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_user_setting", sequenceName = "sq_user_setting")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class UserSetting extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_user_setting")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "account_selected_id", nullable = false)
	private Account accountSelected;

}
