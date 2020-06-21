package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ChitBean;
import com.pavan.modal.User;

@Service
public interface ChitService {

	public void saveChit(User currentUser, ChitBean chit) throws Exception;

	public ApiResponse getChits(User currentUser);

	public ApiResponse getChit(Long id);

	public ApiResponse deleteChit(Long id);

	public ApiResponse deleteChits(User currentUser);

}
