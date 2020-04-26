package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;

@Service
public interface LoginService {

	public ApiResponse login(String username, String password);

	public User getUserFromToken(String userToken);
	
}
