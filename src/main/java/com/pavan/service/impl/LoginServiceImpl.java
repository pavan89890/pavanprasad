package com.pavan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;
import com.pavan.repository.UserRespository;
import com.pavan.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRespository userRepository;

	@Override
	public ApiResponse login(String username, String password) {

		User user = userRepository.findByEmailAndPassword(username, password);

		String message = "";
		if (user == null) {
			message = "Invalid Credentials";
		} else {
			message = "Successfully logged in";
		}

		return new ApiResponse(HttpStatus.OK, message, user);
	}

}