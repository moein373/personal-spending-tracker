package com.moein.expensetracker.controller;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.ui.ExpenseView;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseControllerTest {

	@Mock
	private ExpenseView expenseView;

	@Mock
	private ExpenseRepository expenseRepository;

	private ExpenseController expenseController;

	@Before
	public void setup() {
		expenseController = new ExpenseController(expenseView, expenseRepository);
	}

	@Test
	public void shouldAddExpense() {
		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		expenseController.addExpense(expense);

		verify(expenseRepository).save(expense);
		verify(expenseView).expenseAdded(expense);
	}
}