package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Chit;

@Repository
public interface ChitRespository extends JpaRepository<Chit, Long> {
	
	public Chit findByMonthAndYear(Integer month, Integer year);

	@Query(value = "from Chit order by year desc")
	List<Chit> getChitsOrderByYearDesc();

}
