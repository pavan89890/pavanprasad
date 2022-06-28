package com.pavan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.FdBean;
import com.pavan.modal.User;

@Service
public interface FdService {

	public void saveFd(FdBean fd,User currentUser) throws Exception;

	public ApiResponse getFds(User currentUser);

	public ApiResponse getFd(Long id);

	public ApiResponse deleteFd(Long id);

	public ApiResponse deleteFds(User currentUser);

	public void bulkUpload(List<List<Object>> data);
	
}
