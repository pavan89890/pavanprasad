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
import com.pavan.beans.JobBean;
import com.pavan.modal.User;
import com.pavan.service.JobService;
import com.pavan.service.UserService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/jobs")
@CrossOrigin("*")
public class JobController {

	@Autowired
	private JobService jobService;
	
	@Autowired
	private UserService userService;
	
	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveJob(@RequestHeader("userToken") String userToken,
			@RequestBody(required = true) JobBean jobBean) {

		try {
			User currentUser = userService.getUserFromToken(userToken);
			if (currentUser != null) {
				jobService.saveJob(currentUser,jobBean);

				if (Utility.isEmpty(jobBean.getId())) {
					message = "Job saved successfully";
				} else {
					message = "Job updated successfully";
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
	public ApiResponse jobs(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return jobService.getJobs(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@GetMapping("/{id}")
	public ApiResponse getJob(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return jobService.getJob(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteJob(@RequestHeader("userToken") String userToken, @PathVariable(value = "id") Long id) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return jobService.deleteJob(id);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}

	@DeleteMapping()
	public ApiResponse deleteJobs(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return jobService.deleteJobs(currentUser);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
	
	@DeleteMapping("/all")
	public ApiResponse deleteAllJobs(@RequestHeader("userToken") String userToken) {
		User currentUser = userService.getUserFromToken(userToken);
		if (currentUser != null) {
			return jobService.deleteJobs(null);
		} else {
			return new ApiResponse(HttpStatus.UNAUTHORIZED, "Unauthorized to acccess this resource", null);
		}
	}
}
