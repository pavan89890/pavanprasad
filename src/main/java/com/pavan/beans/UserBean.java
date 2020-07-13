package com.pavan.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {
	private Long id;
	private String name;
	private String email;
	private String mobile;
	private String password;
	private Date oriDob;
	private Date cerDob;
	private String oriAgeStr;
	private String cerAgeStr;
	private String userToken;
}
