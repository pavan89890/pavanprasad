package com.pavan.rest.controller;

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
import com.pavan.modal.Job;
import com.pavan.service.JobService;
import com.pavan.util.Utility;

@RestController
@RequestMapping(path = "/api/jobs")
@CrossOrigin("*")
public class JobController {

	@Autowired
	private JobService jobService;

	@PostMapping
	public ApiResponse saveJob(@RequestBody(required = true) JobBean jobBean) {

		Job job = new Job();
		if (jobBean.getId() != null) {
			job.setId(jobBean.getId());
		}
		job.setCompany(jobBean.getCompany());

		try {
			if (!Utility.isEmpty(jobBean.getDojStr())) {

				job.setDoj(Utility.yyyy_MM_dd.parse(jobBean.getDojStr()));

			}

			if (!Utility.isEmpty(jobBean.getDolStr())) {
				job.setDol(Utility.yyyy_MM_dd.parse(jobBean.getDolStr()));
			}

		} catch (Exception e) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),null);
		}

		return jobService.saveJob(job);

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
