package com.moein.expensetracker.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.repository.mongo.ExpenseMongoRepository;
import com.moein.expensetracker.ui.ExpenseTrackerFrame;

public class ExpenseTrackerE2EIT {

	private MongoClient mongoClient;
	private ExpenseRepository expenseRepository;
	private ExpenseTrackerFrame expenseTrackerFrame;
	private FrameFixture window;
	private Robot robot;

	@Before
	public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		mongoClient = new MongoClient("localhost", 27017);

		expenseRepository = new ExpenseMongoRepository(mongoClient);

		ExpenseRecord existing = expenseRepository.findById("1");

		if (existing != null) {
			expenseRepository.delete("1");
		}

		GuiActionRunner.execute(() -> {
			expenseTrackerFrame = new ExpenseTrackerFrame();

			ExpenseController expenseController = new ExpenseController(expenseTrackerFrame, expenseRepository);

			expenseTrackerFrame.setExpenseController(expenseController);

			expenseController.loadAllExpenses();

			return expenseTrackerFrame;
		});

		window = new FrameFixture(robot, expenseTrackerFrame);

		window.show();
	}

	@After
	public void tearDown() {
		ExpenseRecord existing = expenseRepository.findById("1");

		if (existing != null) {
			expenseRepository.delete("1");
		}

		if (window != null) {
			window.cleanUp();
		}

		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	@Test
	public void shouldPerformCompleteCrudFlow() {
		window.textBox("idTextBox").enterText("1");

		window.textBox("descriptionTextBox").enterText("Coffee");

		window.textBox("amountTextBox").enterText("3.50");

		window.textBox("categoryTextBox").enterText("Food");

		window.textBox("dateTextBox").enterText("2026-09-04");

		window.button(JButtonMatcher.withText("Add")).requireEnabled().click();

		ExpenseRecord addedExpense = expenseRepository.findById("1");

		assertNotNull(addedExpense);

		assertEquals("Coffee", addedExpense.getDescription());

		assertEquals(3.50, addedExpense.getAmount(), 0.001);

		assertEquals("Food", addedExpense.getCategory());

		assertEquals(LocalDate.of(2026, 9, 4), addedExpense.getDate());

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable").selectRows(0);

		window.textBox("descriptionTextBox").setText("Dinner");

		window.textBox("amountTextBox").setText("25.00");

		window.button(JButtonMatcher.withText("Update")).click();

		ExpenseRecord updatedExpense = expenseRepository.findById("1");

		assertNotNull(updatedExpense);

		assertEquals("Dinner", updatedExpense.getDescription());

		assertEquals(25.00, updatedExpense.getAmount(), 0.001);

		window.table("expenseTable")
				.requireContents(new String[][] { { "1", "Dinner", "25.0", "Food", "2026-09-04" } });

		window.button(JButtonMatcher.withText("Delete")).click();

		assertNull(expenseRepository.findById("1"));

		window.table("expenseTable").requireRowCount(0);
	}
}