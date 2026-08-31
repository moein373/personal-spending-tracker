package com.moein.expensetracker.repository;

import java.util.List;

import com.moein.expensetracker.model.ExpenseRecord;

public interface ExpenseRepository {

	void save(ExpenseRecord expense);

	List<ExpenseRecord> findAll();

	ExpenseRecord findById(String id);

	void delete(String id);

	void update(ExpenseRecord expense);
}