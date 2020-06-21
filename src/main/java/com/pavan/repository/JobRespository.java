package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Job;
import com.pavan.modal.User;

@Repository
public interface JobRespository extends JpaRepository<Job, Long> {
	public Job findByUserAndCompany(User user, String name);

	public List<Job> findByUserOrderByDojDesc(User user);
	
	@Modifying
	@Query("delete from Job j where j.user=:user")
	public void deleteByUser(@Param("user") User user);
}
