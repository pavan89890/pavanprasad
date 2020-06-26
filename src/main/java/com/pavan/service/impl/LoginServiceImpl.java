package com.pavan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.UserBean;
import com.pavan.service.LoginService;
import com.pavan.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserService userService;

	@Override
	public ApiResponse login(String username, String password) {

		UserBean userBean = userService.getUser(username, password);

		String message = "";
		if (userBean == null) {
			message = "Invalid Credentials";
		} else {
			message = "Successfully logged in";
		}
		return new ApiResponse(HttpStatus.OK, message, userBean);
	}
}