package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.BankBean;
import com.pavan.modal.User;

@Service
public interface BankService {

	public void saveBank(User currentUser, BankBean bank) throws Exception;

	public ApiResponse getBanks(User currentUser);

	public ApiResponse getBank(Long id);

	public ApiResponse deleteBank(Long id);

	public ApiResponse deleteBanks(User currentUser);

}
