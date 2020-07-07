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
import com.pavan.beans.MutualFundBean;
import com.pavan.modal.User;
import com.pavan.service.MutualFundService;
import com.pavan.service.UserService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/mfs")
@CrossOrigin("*")
public class MutualFundsController {

	@Autowired
	private UserService userService;

	@Autowired
	private MutualFundService mutualFundService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveMutualFund(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) MutualFundBean mfBean) {

		try {
			User currentUser = userService.getUserFromToken(userToken);
			if (currentUser != null) {
				mutualFundService.saveMutualFund(mfBean, currentUser);

				if (Utility.isEmpty(mfBean.getId())) {
					message = "Mutual Fund saved successfully";
				} else {
					message = "Mutual Fund updated successfully";
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
	public ApiResponse mutualFunds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return mutualFundService.getMutualFunds(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getMutualFund(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return mutualFundService.getMutualFund(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteMutualFund(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return mutualFundService.deleteMutualFund(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping
	public ApiResponse deleteMutualFunds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return mutualFundService.deleteMutualFunds(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
	
	@DeleteMapping("/all")
	public ApiResponse deleteAllMutualFunds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return mutualFundService.deleteMutualFunds(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
