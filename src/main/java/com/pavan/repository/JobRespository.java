package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Job;

@Repository
public interface JobRespository extends JpaRepository<Job, Long> {
	public Job findByCompany(String name);

	List<Job> findAllByOrderByDojDesc();
}
