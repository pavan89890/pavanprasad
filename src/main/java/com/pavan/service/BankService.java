package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.BankBean;

@Service
public interface BankService {

	public void saveBank(BankBean bank) throws Exception;

	public ApiResponse getBanks();

	public ApiResponse getBank(Long id);

	public ApiResponse deleteBank(Long id);

	public ApiResponse deleteBanks();

}
