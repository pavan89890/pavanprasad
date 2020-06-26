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
import com.pavan.beans.FdBean;
import com.pavan.modal.User;
import com.pavan.service.FdService;
import com.pavan.service.UserService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/fds")
@CrossOrigin("*")
public class FdController {

	@Autowired
	private UserService userService;

	@Autowired
	private FdService fdService;

	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveFd(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) FdBean fdBean) {

		try {
			User currentUser = userService.getUserFromToken(userToken);
			if (currentUser != null) {
				fdService.saveFd(fdBean, currentUser);

				if (Utility.isEmpty(fdBean.getId())) {
					message = "Fd saved successfully";
				} else {
					message = "Fd updated successfully";
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
	public ApiResponse fds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return fdService.getFds(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getFd(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return fdService.getFd(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteFd(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return fdService.deleteFd(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping
	public ApiResponse deleteFds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return fdService.deleteFds(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
	
	@DeleteMapping("/all")
	public ApiResponse deleteAllFds(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return fdService.deleteFds(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
