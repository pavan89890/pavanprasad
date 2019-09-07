package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Fd;

@Service
public interface FdService {

	public ApiResponse saveFd(Fd fd);

	public ApiResponse getFds();

	public ApiResponse getFd(Long id);

	public ApiResponse deleteFd(Long id);

	public ApiResponse deleteFds();

}
