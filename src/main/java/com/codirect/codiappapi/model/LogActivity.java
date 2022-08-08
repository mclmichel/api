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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= {"id"}, callSuper = false)
@Table(name = "log_activity")
@Entity
@SequenceGenerator(allocationSize = 1, name = "gen_log_activity", sequenceName = "sq_log_activity")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class LogActivity extends BaseEntity {

	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(generator = "gen_log_activity")
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(length = 1000)
	private String method;
	
	@Column(length = 2500)
	private String paramter;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "int8")
	private User user;
}
