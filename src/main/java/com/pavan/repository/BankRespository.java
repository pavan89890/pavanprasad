package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Bank;

@Repository
public interface BankRespository extends JpaRepository<Bank, Long> {
	public Bank findByName(String name);
	
	@Query(value = "select sum(balance) from Bank")
	public Float getTotalBalance();
}
