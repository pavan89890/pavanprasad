package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Bank;

@Service
public interface BankService {

	public ApiResponse saveBank(Bank bank);

	public ApiResponse getBanks();

	public ApiResponse getBank(Long id);

	public ApiResponse deleteBank(Long id);

	public ApiResponse deleteBanks();

}
