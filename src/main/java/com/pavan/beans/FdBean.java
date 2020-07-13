package com.pavan.beans;

import java.util.Date;

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
	private Date depositedOn;
	private Integer periodInMonths;
	private Date maturedOn;
	private String remainingTime;
	private Float profit;
}
