package com.pavan.service;

import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ExpenseBean;
import com.pavan.modal.User;

@Service
public interface ExpenseService {

	public void saveExpense(User user,ExpenseBean expenseBean) throws Exception;

	public ApiResponse getExpenses(User user,String expenseType);

	public ApiResponse getExpense(Long id);

	public ApiResponse deleteExpense(Long id);
	
	public ApiResponse deleteExpenses(User user);

}
