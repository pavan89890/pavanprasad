package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.FixedDeposit;

@Service
public interface FixedDepositService {

	public ApiResponse saveFixedDeposit(FixedDeposit fd);

	public ApiResponse getFixedDeposits();

	public ApiResponse getFixedDeposit(Long id);

	public ApiResponse deleteFixedDeposit(Long id);

}
