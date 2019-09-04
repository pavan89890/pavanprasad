package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Expense;

@Service
public interface ExpenseService {

	public ApiResponse saveExpense(Expense expense);

	public ApiResponse getExpenses();

	public ApiResponse getExpense(Long id);

	public ApiResponse deleteExpense(Long id);

}
