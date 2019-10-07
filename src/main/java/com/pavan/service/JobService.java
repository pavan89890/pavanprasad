package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.JobBean;

@Service
public interface JobService {

	public void saveJob(JobBean job) throws Exception;

	public ApiResponse getJobs();

	public ApiResponse getJob(Long id);

	public ApiResponse deleteJob(Long id);

	public ApiResponse deleteJobs();

}
