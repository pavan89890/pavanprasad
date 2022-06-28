package com.pavan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.MutualFundBean;
import com.pavan.modal.User;

@Service
public interface MutualFundService {

	public void saveMutualFund(MutualFundBean fd,User currentUser) throws Exception;

	public ApiResponse getMutualFunds(User currentUser);

	public ApiResponse getMutualFund(Long id);

	public ApiResponse deleteMutualFund(Long id);

	public ApiResponse deleteMutualFunds(User currentUser);

	public void bulkUpload(List<List<Object>> data);
	
}
