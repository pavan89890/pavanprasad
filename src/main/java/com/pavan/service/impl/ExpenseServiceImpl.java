package com.pavan.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pavan.beans.ApiResponse;
import com.pavan.beans.ExpenseBean;
import com.pavan.modal.Expense;
import com.pavan.modal.User;
import com.pavan.repository.ExpenseRepository;
import com.pavan.service.ExpenseService;
import com.pavan.util.DateUtil;
import com.pavan.util.Utility;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseRepository expenseRepository;

	private String message = "";

	@Override
	public ApiResponse getExpenses(User user, String expenseType) {
		Map<String, Object> data = new LinkedHashMap<>();

		List<Expense> expenses = null;
		if (!Utility.isEmpty(expenseType)) {
			expenses = expenseRepository.getUserExpensesByTypeOrderByDateDesc(expenseType,user);
		} else {
			expenses = expenseRepository.getUserExpensesOrderByDateDesc(user);
		}

		List<ExpenseBean> expenseBeans = new ArrayList<ExpenseBean>();

		Float totalExpense=0f;
		for (Expense expense : expenses) {
			ExpenseBean expenseBean = toBean(expense);
			totalExpense+=expenseBean.getAmount()!=null?expense.getAmount():0f;
			expenseBeans.add(expenseBean);
		}

		if (Utility.isEmpty(expenses)) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		}

		data.put("expenses", expenseBeans);
		data.put("totalExpenses", Utility.formatNumber(totalExpense));

		return new ApiResponse(HttpStatus.OK, null, data);
	}

	private ExpenseBean toBean(Expense expense) {
		ExpenseBean expenseBean = new ExpenseBean();
		expenseBean.setId(expense.getId());
		expenseBean.setName(expense.getName());
		expenseBean.setAmount(Utility.formatNumber(expense.getAmount()));
		if (expense.getDate() != null) {
			expenseBean.setExpenseDateStr(DateUtil.yyyy_MM_dd.format(expense.getDate()));
		}
		expenseBean.setExpenseType(expense.getExpenseType());
		return expenseBean;
	}

	@Override
	public void saveExpense(User user, ExpenseBean expenseBean) throws Exception {

		if (!validData(expenseBean)) {
			throw new Exception(message);
		}

		Expense expense = new Expense();
		if (expenseBean.getId() != null) {
			expense.setId(expenseBean.getId());
		}
		expense.setUser(user);
		expense.setExpenseType(expenseBean.getExpenseType());
		expense.setName(expenseBean.getName());
		expense.setAmount(Utility.formatNumber(expenseBean.getAmount()));
		if (!Utility.isEmpty(expenseBean.getExpenseDateStr())) {
			expense.setDate(DateUtil.yyyy_MM_dd.parse(expenseBean.getExpenseDateStr()));
		}
		expenseRepository.save(expense);
	}

	private boolean validData(ExpenseBean bean) {

		if (Utility.isEmpty(bean.getName())) {
			message = "Please Enter Name";
			return false;
		}

		if (bean.getAmount() == null) {
			message = "Please Enter Amount";
			return false;
		}

		return true;
	}

	@Override
	public ApiResponse getExpense(Long id) {
		if (id == null || id == 0) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		} else {

			Optional<Expense> expenseOp = expenseRepository.findById(id);
			if (expenseOp.isPresent()) {
				ExpenseBean expense = toBean(expenseOp.get());
				return new ApiResponse(HttpStatus.OK, null, expense);
			} else {
				return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
			}
		}
	}

	@Override
	public ApiResponse deleteExpense(Long id) {
		if (getExpense(id).getData() == null) {
			return new ApiResponse(HttpStatus.NOT_FOUND, "No data found", null);
		} else {
			expenseRepository.deleteById(id);
			message = "Expense deleted successfully";
			return new ApiResponse(HttpStatus.OK, message, null);
		}
	}

	@Override
	public ApiResponse deleteExpenses(User user) {
		if (user != null) {
			expenseRepository.deleteByUser(user);
			message = "Hi " + user.getName() + ",all your events deleted successfully";
		} else {
			expenseRepository.deleteAll();
			message = "Expenses deleted successfully";
		}
		return new ApiResponse(HttpStatus.OK, message, null);
	}

}
