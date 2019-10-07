package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ChitBean;

@Service
public interface ChitService {

	public void saveChit(ChitBean chit) throws Exception;

	public ApiResponse getChits();

	public ApiResponse getChit(Long id);

	public ApiResponse deleteChit(Long id);

	public ApiResponse deleteChits();

}
