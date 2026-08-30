package com.moein.expensetracker.repository;

import java.util.List;

import com.moein.expensetracker.model.ExpenseRecord;

public interface ExpenseRepository {

	void save(ExpenseRecord expense);

	List<ExpenseRecord> findAll();
}