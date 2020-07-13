package com.pavan.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutualFundBean {
	private Long id;
	private String name;
	private String desc;
	private Float investedAmount;
	private Float currentAmount;
	private Date depositedOn;
	private Float profitOrLossAmount;
	private Float profitOrLossPerc;
}
