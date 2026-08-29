package com.moein.expensetracker.model;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.Test;

public class ExpenseRecordTest {

	@Test
	public void constructorWithZeroAmountShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord("Lunch", 0.0, "Food", LocalDate.of(2026, 8, 29)));
	}
}