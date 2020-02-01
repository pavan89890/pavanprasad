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
	public void saveJob(JobBean jobBean) throws Exception {

		if (!validData(jobBean)) {
			throw new Exception(message);
		}

		Job job = new Job();
		if (jobBean.getId() != null) {
			job.setId(jobBean.getId());
		}
		job.setCompany(jobBean.getCompany());
		job.setDesignation(jobBean.getDesignation());
		job.setCurrent(jobBean.getCurrent()==null?false:jobBean.getCurrent());
		try {
			if (!Utility.isEmpty(jobBean.getDojStr())) {

				job.setDoj(Utility.yyyy_MM_dd.parse(jobBean.getDojStr()));

			}

			if (!Utility.isEmpty(jobBean.getDolStr())) {
				job.setDol(Utility.yyyy_MM_dd.parse(jobBean.getDolStr()));
			}

		} catch (Exception e) {
			message = e.getMessage();
			throw new Exception(message);
		}

		jobsRepository.save(job);

	}

	private boolean validData(JobBean bean) {

		if (Utility.isEmpty(bean.getCompany())) {
			message = "Please Enter Company Name";
			return false;
		}
		
		if (Utility.isEmpty(bean.getDesignation())) {
			message = "Please Enter Designation";
			return false;
		}

		if (Utility.isEmpty(bean.getDojStr())) {
			message = "Please Select DOJ";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getJobs() {

		Map<String, Object> data = new LinkedHashMap<>();

		List<Job> jobs = jobsRepository.findAllByOrderByDojDesc();

		if (Utility.isEmpty(jobs)) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		}

		List<JobBean> jobBeans = new ArrayList<>();

		LocalDate now = LocalDate.now();

		for (Job job : jobs) {
			JobBean jobBean = new JobBean();

			jobBean.setId(job.getId());
			jobBean.setCompany(job.getCompany());
			jobBean.setDesignation(job.getDesignation());
			jobBean.setCurrent(job.getCurrent()==null?false:job.getCurrent());

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
			LocalDate firstDoj = jobs.get(jobs.size() - 1).getDoj().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			totalExperience = Utility.getDateDifference(firstDoj, LocalDate.now());
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
