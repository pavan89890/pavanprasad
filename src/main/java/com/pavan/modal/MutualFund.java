package com.pavan.modal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_mutual_fund")
public class MutualFund extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String desc;

	@Column(name = "INVESTED_AMOUNT")
	private Float investedAmount;

	@Column(name = "CURRENT_AMOUNT")
	private Float currentAmount;

	@Column(name = "DEPOSITED_ON")
	private Date depositedOn;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

}
