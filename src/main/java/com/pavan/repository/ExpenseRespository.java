package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Expense;

@Repository
public interface ExpenseRespository extends JpaRepository<Expense, Long> {
	public Expense findByName(String name);
	
	@Query(value="from Expense order by createdOn desc")
	public List<Expense> getExpensesOrderByDateDesc();

	@Query(value = "select sum(amount) from Expense")
	public Float getTotalExpenseAmount();
}
