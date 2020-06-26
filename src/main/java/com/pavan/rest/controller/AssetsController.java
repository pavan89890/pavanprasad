package com.pavan.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.User;
import com.pavan.service.AssetService;
import com.pavan.service.UserService;

@RestController
@RequestMapping(path = "/api/assets")
@CrossOrigin("*")
public class AssetsController {

	@Autowired
	private AssetService assetService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ApiResponse assets(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return assetService.getAssets(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

}
