package com.moein.expensetracker.ui;

import java.util.List;

import com.moein.expensetracker.model.ExpenseRecord;

public interface ExpenseView {

	void showAllExpenses(List<ExpenseRecord> expenses);

	void expenseAdded(ExpenseRecord expense);

	void expenseRemoved(ExpenseRecord expense);
}