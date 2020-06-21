package com.pavan.rest.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ChitBean;
import com.pavan.modal.User;
import com.pavan.service.ChitService;
import com.pavan.service.LoginService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/chits")
@CrossOrigin("*")
public class ChitController {

	@Autowired
	private ChitService chitService;

	@Autowired
	private LoginService loginService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveChit(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) ChitBean chitBean) {

		try {

			User currentUser = loginService.getUserFromToken(userToken);
			if (currentUser != null) {
				chitService.saveChit(currentUser, chitBean);

				if (Utility.isEmpty(chitBean.getId())) {
					message = "Chit saved successfully";
				} else {
					message = "Chit updated successfully";
				}
				return new ApiResponse(HttpStatus.OK, message, null);
			} else {
				return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
			}

		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse chits(@RequestHeader("userToken") String userToken) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return chitService.getChits(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getChit(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return chitService.getChit(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteChit(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return chitService.deleteChit(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping
	public ApiResponse deleteUserChits(@RequestHeader("userToken") String userToken) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return chitService.deleteChits(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping("/all")
	public ApiResponse deleteChits(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return chitService.deleteChits(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
