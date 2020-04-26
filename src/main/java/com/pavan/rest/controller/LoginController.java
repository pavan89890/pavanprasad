package com.pavan.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.service.LoginService;

@RestController
@RequestMapping(path = "/api/login")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ApiResponse login(@RequestBody Map<String,String> data) {
		return loginService.login(data.get("username"), data.get("password"));
	}

}
