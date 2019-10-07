package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.FdBean;

@Service
public interface FdService {

	public void saveFd(FdBean fd) throws Exception;

	public ApiResponse getFds();

	public ApiResponse getFd(Long id);

	public ApiResponse deleteFd(Long id);

	public ApiResponse deleteFds();

}
