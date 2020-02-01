package com.pavan.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobBean {
	private Long id;
	private String company;
	private String designation;
	private String dojStr;
	private String dolStr;
	private String experience;
	private Boolean current;
}
