package com.pavan.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.JobBean;
import com.pavan.modal.Job;
import com.pavan.repository.JobRespository;
import com.pavan.service.JobService;
import com.pavan.util.Utility;

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

		Map<String, Object> data = new LinkedHashMap<>();

		List<Job> jobs = jobsRepository.getJobsOrderByDoj();

		if (Utility.isEmpty(jobs)) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		}

		List<JobBean> jobBeans = new ArrayList<>();

		LocalDate now = LocalDate.now();

		for (Job job : jobs) {
			JobBean jobBean = new JobBean();

			jobBean.setId(job.getId());
			jobBean.setCompany(job.getCompany());

			if (job.getDoj() != null) {
				jobBean.setDojStr(Utility.yyyy_MM_dd.format(job.getDoj()));
			}

			if (job.getDol() != null) {
				jobBean.setDolStr(Utility.yyyy_MM_dd.format(job.getDol()));
			}

			LocalDate date1 = job.getDoj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (job.getDol() != null) {
				now = job.getDol().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}

			jobBean.setExperience(Utility.getDateDifference(date1, now));

			jobBeans.add(jobBean);
		}

		String totalExperience = "";

		if (!Utility.isEmpty(jobs)) {
			LocalDate firstDoj = jobs.get(0).getDoj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			totalExperience = Utility.getDateDifference(firstDoj, now);
		}

		data.put("jobs", jobBeans);
		data.put("totalExperience", totalExperience);

		return new ApiResponse(HttpStatus.OK, null, data);
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

	@Override
	public ApiResponse deleteJobs() {
		jobsRepository.deleteAll();
		message = "Jobs deleted successfully";
		return new ApiResponse(HttpStatus.OK, message, null);
	}

}
