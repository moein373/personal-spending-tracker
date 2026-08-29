package com.moein.expensetracker.model;

import java.time.LocalDate;

public class ExpenseRecord {

	private String description;

	public ExpenseRecord(String description, double amount, String category, LocalDate date) {

		if (amount <= 0.0) {
			throw new IllegalArgumentException();
		}

		if (description == null || description.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}