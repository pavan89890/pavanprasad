package com.pavan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.JobBean;
import com.pavan.modal.User;

@Service
public interface JobService {

	public void saveJob(User user,JobBean job) throws Exception;

	public ApiResponse getJobs(User user);

	public ApiResponse getJob(Long id);

	public ApiResponse deleteJob(Long id);

	public ApiResponse deleteJobs(User user);

	public void bulkUpload(List<List<Object>> data);

}
