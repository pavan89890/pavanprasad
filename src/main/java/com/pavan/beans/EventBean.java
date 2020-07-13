package com.pavan.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBean {

	private Long id;
	
	private String eventType;

	private String eventName;
	
	private Date eventDate;

	private boolean isToday;
}
