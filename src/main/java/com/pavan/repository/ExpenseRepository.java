package com.pavan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pavan.modal.Expense;
import com.pavan.modal.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	@Query(value="from Expense where user=:user order by date desc,updatedOn desc")
	public List<Expense> getUserExpensesOrderByDateDesc(@Param("user") User user);
	
	@Query(value="from Expense where user=:user and expenseType=:expenseType order by date desc,updatedOn desc")
	public List<Expense> getUserExpensesByTypeOrderByDateDesc(@Param("expenseType") String expenseType,@Param("user") User user);

	@Modifying
	@Query("delete from Expense e where e.user=:user")
	public void deleteByUser(@Param("user") User user);

}
