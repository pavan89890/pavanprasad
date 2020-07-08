package com.pavan.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBean {

	private Long id;
	
	private String eventType;

	private String eventName;
	
	private String eventDateStr;

	private boolean isToday;
}
