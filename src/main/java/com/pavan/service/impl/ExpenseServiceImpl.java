package com.pavan.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.modal.Expense;
import com.pavan.repository.ExpenseRespository;
import com.pavan.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseRespository expenseRepository;

	private String message = "";

	@Override
	public ApiResponse getExpenses() {
		if (expenseRepository.findAll() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		}
		return new ApiResponse(HttpStatus.OK, null, expenseRepository.findAll());
	}

	@Override
	public ApiResponse saveExpense(Expense expense) {
		Expense c = expenseRepository.findByName(expense.getName());

		if (c != null) {
			if ((expense.getId() == null) || (expense.getId() != c.getId())) {
				return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Expense Name Already Exists", null);
			}
		}

		if (expense.getId() == null || expense.getId() == 0) {
			message = "Expense saved successfully";
		} else {
			message = "Expense updated successfully";
		}
		expenseRepository.save(expense);

		return new ApiResponse(HttpStatus.OK, message, null);
	}

	@Override
	public ApiResponse getExpense(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {

			Optional<Expense> expenseOp = expenseRepository.findById(id);
			if (expenseOp.isPresent()) {
				Expense expense = expenseOp.get();
				return new ApiResponse(HttpStatus.OK, null, expense);
			} else {
				return new ApiResponse(HttpStatus.NO_CONTENT, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteExpense(Long id) {
		if (getExpense(id).getData() == null) {
			return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No data found", null);
		} else {
			expenseRepository.delete((Expense) getExpense(id).getData());
			message = "Expense deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

}
