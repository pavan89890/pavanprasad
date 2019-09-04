package com.pavan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Expense;

@Repository
public interface ExpenseRespository extends JpaRepository<Expense, Long> {
	public Expense findByName(String name);
}
