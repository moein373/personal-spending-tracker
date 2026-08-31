package com.moein.expensetracker.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;

public class ExpenseServiceTest {

	private ExpenseRepository expenseRepository;
	private ExpenseService expenseService;

	@Before
	public void setup() {
		expenseRepository = mock(ExpenseRepository.class);
		expenseService = new ExpenseService(expenseRepository);
	}

	@Test
	public void addExpenseShouldSaveExpenseInRepository() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 30));

		expenseService.addExpense(expense);

		verify(expenseRepository).save(expense);
	}

	@Test
	public void getAllExpensesShouldReturnExpensesFromRepository() {
		ExpenseRecord expense = new ExpenseRecord("Lunch", 12.50, "Food", LocalDate.of(2026, 8, 30));

		List<ExpenseRecord> expenses = Arrays.asList(expense);

		when(expenseRepository.findAll()).thenReturn(expenses);

		assertEquals(expenses, expenseService.getAllExpenses());
	}

	@Test
	public void getExpenseByIdShouldReturnExpenseFromRepository() {
		ExpenseRecord expense = new ExpenseRecord("1", "Lunch", 12.50, "Food", LocalDate.of(2026, 8, 30));

		when(expenseRepository.findById("1")).thenReturn(expense);

		assertEquals(expense, expenseService.getExpenseById("1"));
	}

	@Test
	public void deleteExpenseShouldDeleteExpenseFromRepository() {
		expenseService.deleteExpense("1");

		verify(expenseRepository).delete("1");
	}

	@Test
	public void updateExpenseShouldUpdateExpenseInRepository() {
		ExpenseRecord expense = new ExpenseRecord("1", "Dinner", 25.00, "Food", LocalDate.of(2026, 8, 30));

		expenseService.updateExpense(expense);

		verify(expenseRepository).update(expense);
	}
}