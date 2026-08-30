package com.moein.expensetracker.service;

import java.util.List;

import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;

public class ExpenseService {

	private ExpenseRepository expenseRepository;

	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public void addExpense(ExpenseRecord expense) {
		expenseRepository.save(expense);
	}

	public List<ExpenseRecord> getAllExpenses() {
		return expenseRepository.findAll();
	}
}