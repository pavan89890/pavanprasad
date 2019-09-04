package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Chit;

@Service
public interface ChitService {

	public ApiResponse saveChit(Chit chit);

	public ApiResponse getChits();

	public ApiResponse getChit(Long id);

	public ApiResponse deleteChit(Long id);

}
