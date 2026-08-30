package com.moein.expensetracker.model;

import java.time.LocalDate;

public class ExpenseRecord {

	private String id;
	private String description;
	private double amount;
	private String category;
	private LocalDate date;

	public ExpenseRecord(String description, double amount, String category, LocalDate date) {

		if (amount <= 0.0) {
			throw new IllegalArgumentException();
		}

		if (description == null || description.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.description = description;
		this.amount = amount;
		this.category = category;
		this.date = date;
	}

	public ExpenseRecord(String id, String description, double amount, String category, LocalDate date) {

		this(description, amount, category, date);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public LocalDate getDate() {
		return date;
	}
	
}