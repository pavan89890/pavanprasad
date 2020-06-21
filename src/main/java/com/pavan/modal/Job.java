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
@Table(name = "t_job")
public class Job extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "DOJ")
	private Date doj;
	
	@Column(name = "DOL")
	private Date dol;
	
	@Column(name = "CURRENT_JOB")
	private Boolean current;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
