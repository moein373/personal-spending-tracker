package com.moein.expensetracker.app;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Test;

import com.moein.expensetracker.repository.ExpenseRepository;

public class ExpenseTrackerApplicationTest {

	@Test
	public void shouldCreateApplicationWithRepository() {
		ExpenseRepository expenseRepository = mock(ExpenseRepository.class);

		ExpenseTrackerApplication application = new ExpenseTrackerApplication(expenseRepository);

		assertNotNull(application);
	}

	@Test
	public void shouldStartApplicationWithInjectedRepository() throws InvocationTargetException, InterruptedException {

		ExpenseRepository expenseRepository = mock(ExpenseRepository.class);

		ExpenseTrackerApplication application = new ExpenseTrackerApplication(expenseRepository);

		application.start();

		SwingUtilities.invokeAndWait(() -> {
		});

		verify(expenseRepository).findAll();
	}

	@Test
	public void shouldCreateApplicationWithDefaultConstructor() {
		ExpenseTrackerApplication application = new ExpenseTrackerApplication();

		assertNotNull(application);
	}

	@Test
	public void shouldRunMainMethod() throws Exception {

		Thread applicationThread = new Thread(() -> ExpenseTrackerApplication.main(new String[0]));

		applicationThread.start();

		Thread.sleep(1000);

		applicationThread.interrupt();

		assertNotNull(applicationThread);
	}
}