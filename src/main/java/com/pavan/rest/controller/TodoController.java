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
import com.pavan.beans.TodoBean;
import com.pavan.modal.User;
import com.pavan.service.TodoService;
import com.pavan.service.UserService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/todo")
@CrossOrigin("*")
public class TodoController {

	@Autowired
	private UserService userService;

	@Autowired
	private TodoService todoService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveTodo(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) TodoBean todoBean) {

		try {

			User currentUser = userService.getUserFromToken(userToken);
			if (currentUser != null) {
				todoService.saveTodo(currentUser, todoBean);

				if (Utility.isEmpty(todoBean.getId())) {
					message = "Todo saved successfully";
				} else {
					message = "Todo updated successfully";
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
	public ApiResponse todos(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return todoService.getTodos(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getTodo(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return todoService.getTodo(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteTodo(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {

		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return todoService.deleteTodo(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}

	}

	@DeleteMapping
	public ApiResponse deleteUserTodos(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return todoService.deleteTodos(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping(value = "/all")
	public ApiResponse deleteTodos(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return todoService.deleteTodos(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
