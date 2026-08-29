package com.moein.expensetracker.model;

import java.time.LocalDate;

public class ExpenseRecord {

	public ExpenseRecord(String description, double amount, String category, LocalDate date) {

		if (amount <= 0.0) {
			throw new IllegalArgumentException();
		}
	}
}