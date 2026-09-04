package com.moein.expensetracker.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

public class ExpenseRecordTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithZeroAmountShouldThrow() {
		new ExpenseRecord("Lunch", 0.0, "Food", LocalDate.of(2026, 8, 29));
	}

	@Test
	public void constructorWithPositiveAmountShouldCreateExpenseRecord() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 29));

		assertNotNull(expense);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithNegativeAmountShouldThrow() {
		new ExpenseRecord("Lunch", -1.0, "Food", LocalDate.of(2026, 8, 29));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithEmptyDescriptionShouldThrow() {
		new ExpenseRecord("", 10.0, "Food", LocalDate.of(2026, 8, 29));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithWhitespaceDescriptionShouldThrow() {
		new ExpenseRecord("   ", 10.0, "Food", LocalDate.of(2026, 8, 29));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithNullDescriptionShouldThrow() {
		new ExpenseRecord(null, 10.0, "Food", LocalDate.of(2026, 8, 29));
	}

	@Test
	public void constructorShouldStoreDescription() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 29));

		assertEquals("Lunch", expense.getDescription());
	}

	@Test
	public void constructorShouldStoreAmount() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 29));

		assertEquals(12.50, expense.getAmount(), 0.0);
	}

	@Test
	public void constructorShouldStoreCategory() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 29));

		assertEquals("Food", expense.getCategory());
	}

	@Test
	public void constructorShouldStoreDate() {
		LocalDate date = LocalDate.of(2026, 8, 30);

		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", date);

		assertEquals(date, expense.getDate());
	}

	@Test
	public void constructorShouldStoreId() {
		ExpenseRecord expense = new ExpenseRecord("1", "Lunch", 12.50, "Food", LocalDate.of(2026, 8, 30));

		assertEquals("1", expense.getId());
	}
}