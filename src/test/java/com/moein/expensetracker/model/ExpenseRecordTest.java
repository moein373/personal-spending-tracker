package com.moein.expensetracker.model;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

public class ExpenseRecordTest {

	@Test
	public void constructorWithZeroAmountShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord("Lunch", 0.0, "Food", LocalDate.of(2026, 8, 29)));
	}

	@Test
	public void constructorWithPositiveAmountShouldCreateExpenseRecord() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 29));

		assertNotNull(expense);
	}

	@Test
	public void constructorWithNegativeAmountShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord("Lunch", -1.0, "Food", LocalDate.of(2026, 8, 29)));
	}

	@Test
	public void constructorWithEmptyDescriptionShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord("", 10.0, "Food", LocalDate.of(2026, 8, 29)));
	}

	@Test
	public void constructorWithWhitespaceDescriptionShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord("   ", 10.0, "Food", LocalDate.of(2026, 8, 29)));
	}

	@Test
	public void constructorWithNullDescriptionShouldThrow() {
		assertThrows(IllegalArgumentException.class,
				() -> new ExpenseRecord(null, 10.0, "Food", LocalDate.of(2026, 8, 29)));
	}
}