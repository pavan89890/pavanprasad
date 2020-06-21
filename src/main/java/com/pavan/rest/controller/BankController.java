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
import com.pavan.beans.BankBean;
import com.pavan.modal.User;
import com.pavan.service.BankService;
import com.pavan.service.LoginService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/banks")
@CrossOrigin("*")
public class BankController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private BankService bankService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveBank(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) BankBean bankBean) {

		try {

			User currentUser = loginService.getUserFromToken(userToken);
			if (currentUser != null) {
				bankService.saveBank(currentUser, bankBean);

				if (Utility.isEmpty(bankBean.getId())) {
					message = "Bank saved successfully";
				} else {
					message = "Bank updated successfully";
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
	public ApiResponse banks(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return bankService.getBanks(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getBank(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return bankService.getBank(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteBank(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {

		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return bankService.deleteBank(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping
	public ApiResponse deleteUserBanks(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return bankService.deleteBanks(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping(value = "/all")
	public ApiResponse deleteBanks(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return bankService.deleteBanks(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
