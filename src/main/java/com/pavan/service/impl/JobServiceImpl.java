package com.pavan.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Job;
import com.pavan.repository.JobRespository;
import com.pavan.service.JobService;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	JobRespository jobsRepository;

	private String message = "";

	@Override
	public ApiResponse saveJob(Job job) {

		if (job.getId() == null || job.getId() == 0) {
			message = "Job saved successfully";
		} else {
			message = "Job updated successfully";
		}
		jobsRepository.save(job);

		return new ApiResponse(HttpStatus.OK, message, null);
	}

	@Override
	public ApiResponse getJobs() {
		if (jobsRepository.findAll() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		}
		return new ApiResponse(HttpStatus.OK, null, jobsRepository.findAll());
	}

	@Override
	public ApiResponse getJob(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {

			Optional<Job> jobOp = jobsRepository.findById(id);
			if (jobOp.isPresent()) {
				Job fd = jobOp.get();
				return new ApiResponse(HttpStatus.OK, null, fd);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteJob(Long id) {
		if (getJob(id).getData() == null) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
		} else {
			jobsRepository.delete((Job) getJob(id).getData());
			message = "Job deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

}
