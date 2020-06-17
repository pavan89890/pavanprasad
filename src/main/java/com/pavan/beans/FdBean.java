package com.pavan.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FdBean {
	private Long id;
	private String bank;
	private String fdrNo;
	private Float depAmount;
	private Float roi;
	private Float maturedAmount;
	private String depositedOnStr;
	private Integer periodInMonths;
	private String maturedOnStr;
	private String remainingTime;
	private Float profit;
}
