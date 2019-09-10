package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Chit;

@Repository
public interface ChitRespository extends JpaRepository<Chit, Long> {
	public Chit findByMonthAndYear(String month, Integer year);

	@Query(value = "select sum(actualAmount) from Chit")
	public Float getTotalDeposited();
}
