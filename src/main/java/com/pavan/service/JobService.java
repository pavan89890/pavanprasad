package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Job;

@Service
public interface JobService {

	public ApiResponse saveJob(Job job);

	public ApiResponse getJobs();

	public ApiResponse getJob(Long id);

	public ApiResponse deleteJob(Long id);

}
