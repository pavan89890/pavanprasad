package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Bank;

@Repository
public interface BankRespository extends JpaRepository<Bank, Long> {
	public Bank findByName(String name);
}
