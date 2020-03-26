package com.pavan.modal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_event")
public class Events extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "EVENT_TYPE")
	private String eventType;

	@Column(name = "EVENT_NAME")
	private String eventName;
	
	@Column(name = "EVENT_DESC")
	private String eventDesc;

	@Column(name = "EVENT_DATE")
	private Date eventDate;
}
