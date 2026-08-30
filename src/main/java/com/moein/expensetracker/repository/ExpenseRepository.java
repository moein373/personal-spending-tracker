package com.moein.expensetracker.repository;

import com.moein.expensetracker.model.ExpenseRecord;

public interface ExpenseRepository {

    void save(ExpenseRecord expense);
}