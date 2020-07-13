package com.pavan.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseBean {
	private Long id;
	private String expenseType;
	private String name;
	private Float amount;
	private Date expenseDate;
}
