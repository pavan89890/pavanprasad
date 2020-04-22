package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;

@Service
public interface LoginService {

	public ApiResponse login(String username,String password);

}
