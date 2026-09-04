package com.moein.expensetracker.controller;

import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.ui.ExpenseView;

public class ExpenseController {

	private ExpenseView expenseView;
	private ExpenseRepository expenseRepository;

	public ExpenseController(ExpenseView expenseView, ExpenseRepository expenseRepository) {
		this.expenseView = expenseView;
		this.expenseRepository = expenseRepository;
	}

	public void addExpense(ExpenseRecord expense) {
		expenseRepository.save(expense);
		expenseView.expenseAdded(expense);
	}

	public void loadAllExpenses() {
		expenseView.showAllExpenses(expenseRepository.findAll());
	}

	public void deleteExpense(ExpenseRecord expense) {
		expenseRepository.delete(expense.getId());
		expenseView.expenseRemoved(expense);
	}

	public void updateExpense(ExpenseRecord expense) {
		expenseRepository.update(expense);
		expenseView.expenseUpdated(expense);
	}
}