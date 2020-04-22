package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.service.LoginService;

@RestController
@RequestMapping(path = "/api/login")
@CrossOrigin("*")
public class LoginRestController {

	@Autowired
	private LoginService loginService;

	@GetMapping
	public ApiResponse login(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		return loginService.login(username,password);
	}

}
