package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ExpenseBean;

@Service
public interface ExpenseService {

	public void saveExpense(ExpenseBean expenseBean) throws Exception;

	public ApiResponse getExpenses();

	public ApiResponse getExpense(Long id);

	public ApiResponse deleteExpense(Long id);
	
	public ApiResponse deleteExpenses();

}
