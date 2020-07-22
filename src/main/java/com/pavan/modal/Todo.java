package com.pavan.modal;

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
@Table(name = "T_TODO")
public class Todo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TODO")
	private String todo;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "STATUS")
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

}
