package com.pavan.modal;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "ORI_DOB")
	private Date oriDob;

	@Column(name = "CER_DOB")
	private Date cerDob;

	@OneToMany(fetch = FetchType.LAZY)
	private List<UserEducation> userEducations;
}
