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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ExpenseBean;
import com.pavan.modal.User;
import com.pavan.service.ExpenseService;
import com.pavan.service.LoginService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private LoginService loginService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveExpense(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) ExpenseBean expenseBean) {

		try {
			User currentUser = loginService.getUserFromToken(userToken);
			if (currentUser != null) {
				expenseService.saveExpense(currentUser, expenseBean);

				if (Utility.isEmpty(expenseBean.getId())) {
					message = "Expense saved successfully";
				} else {
					message = "Expense updated successfully";
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
	public ApiResponse expenses(@RequestHeader("userToken") String userToken,
			@RequestParam(value = "expenseType", required = false) String expenseType) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return expenseService.getExpenses(currentUser, expenseType);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getExpense(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return expenseService.getExpense(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteExpense(@RequestHeader("userToken") String userToken,
			@PathVariable(value = "id") Long id) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return expenseService.deleteExpense(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping()
	public ApiResponse deleteExpenses(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return expenseService.deleteExpenses(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/all")
	public ApiResponse deleteAllExpenses(@RequestHeader("userToken") String userToken) {
		User currentUser = loginService.getUserFromToken(userToken);
		if (currentUser != null) {
			return expenseService.deleteExpenses(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
