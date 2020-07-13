package com.pavan.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobBean {
	private Long id;
	private String company;
	private String designation;
	private Date doj;
	private Date dol;
	private String experience;
	private Boolean current;
}
