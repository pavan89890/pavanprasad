package com.pavan.rest.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.FixedDepositBean;
import com.pavan.modal.FixedDeposit;
import com.pavan.service.FixedDepositService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/fds/")
@CrossOrigin("*")
public class FixedDepositController {

	@Autowired
	private FixedDepositService fixedDepositService;

	@PostMapping
	public ApiResponse saveFixedDeposit(@RequestBody(required = true) FixedDepositBean fixedDepositBean) {

		FixedDeposit fixedDeposit = new FixedDeposit();
		if (fixedDepositBean.getId() != null) {
			fixedDeposit.setId(fixedDepositBean.getId());
		}
		fixedDeposit.setBank(fixedDepositBean.getBank());
		fixedDeposit.setDepAmount(fixedDepositBean.getDepAmount());
		fixedDeposit.setRoi(fixedDepositBean.getRoi());

		Float maturedAmount = 0f;
		if (fixedDepositBean.getDepAmount() != null) {
			maturedAmount = (fixedDepositBean.getDepAmount() * (fixedDepositBean.getRoi() / 100));
		}

		fixedDeposit.setMaturedAmount(fixedDepositBean.getDepAmount()+maturedAmount);

		Date depositedOn = null;

		if (!Utility.isEmpty(fixedDepositBean.getDepositedOnStr())) {
			try {
				depositedOn = Utility.onlyDateSdf.parse(fixedDepositBean.getDepositedOnStr());
			} catch (ParseException e) {
				return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
			}
		}

		fixedDeposit.setDepositedOn(depositedOn);
		fixedDeposit.setPeriodInMonths(fixedDepositBean.getPeriodInMonths());
		
		Date maturedOn=null;
		
		if (!Utility.isEmpty(depositedOn) && !Utility.isEmpty(fixedDepositBean.getPeriodInMonths())) {
			Calendar c=Calendar.getInstance();
			c.setTime(depositedOn);
			c.add(Calendar.MONTH, fixedDepositBean.getPeriodInMonths());
			maturedOn = c.getTime();
		}
		
		fixedDeposit.setMaturedOn(maturedOn);
		
		return fixedDepositService.saveFixedDeposit(fixedDeposit);

	}

	@GetMapping
	public ApiResponse fds() {
		return fixedDepositService.getFixedDeposits();
	}

	@GetMapping("/{id}")
	public ApiResponse getFixedDeposit(@PathVariable(value = "id") Long id) {
		return fixedDepositService.getFixedDeposit(id);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteFixedDeposit(@PathVariable(value = "id") Long id) {
		return fixedDepositService.deleteFixedDeposit(id);
	}
}
