package com.pavan.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseBean {
	private Long id;
	private String expenseType;
	private String name;
	private Float amount;
	private String expenseDateStr;
}
