package com.moein.expensetracker.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

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
        ExpenseRecord expense = new ExpenseRecord(
                "Lunch",
                12.50,
                "Food",
                LocalDate.of(2026, 8, 30));

        expenseService.addExpense(expense);

        verify(expenseRepository).save(expense);
    }
}