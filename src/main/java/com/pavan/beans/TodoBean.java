package com.pavan.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoBean {
	private Long id;
	private String todo;
	private Integer priority;
	private boolean status;
}
