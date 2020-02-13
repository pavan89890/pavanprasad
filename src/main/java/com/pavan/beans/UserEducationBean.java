package com.pavan.beans;

import java.util.List;

import com.pavan.modal.UserEducation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEducationBean {
	private Long id;
	private String name;
	private String mobile;
	private List<UserEducation> userEducations;
}
