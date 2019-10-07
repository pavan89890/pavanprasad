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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.JobBean;
import com.pavan.service.JobService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/jobs")
@CrossOrigin("*")
public class JobController {

	@Autowired
	private JobService jobService;
	private String message;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ApiResponse saveJob(@RequestBody(required = true) JobBean jobBean) {

		try {
			jobService.saveJob(jobBean);

			if (Utility.isEmpty(jobBean.getId())) {
				message = "Job saved successfully";
			} else {
				message = "Job updated successfully";
			}

			return new ApiResponse(HttpStatus.OK, message, null);
		} catch (Exception e) {
			message = "Error-" + e.getMessage();
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
		}

	}

	@GetMapping
	public ApiResponse jobs() {
		return jobService.getJobs();
	}

	@GetMapping("/{id}")
	public ApiResponse getJob(@PathVariable(value = "id") Long id) {
		return jobService.getJob(id);
	}

	@DeleteMapping("/{id}")
	public ApiResponse deleteJob(@PathVariable(value = "id") Long id) {
		return jobService.deleteJob(id);
	}

	@DeleteMapping()
	public ApiResponse deleteJobs() {
		return jobService.deleteJobs();
	}
}
