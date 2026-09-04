package com.moein.expensetracker.integration;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.List;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.repository.mongo.ExpenseMongoRepository;
import com.moein.expensetracker.ui.ExpenseTrackerFrame;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@RunWith(GUITestRunner.class)
public class ExpenseTrackerIntegrationTest extends AssertJSwingJUnitTestCase {

	private MongoServer mongoServer;
	private MongoClient mongoClient;
	private ExpenseRepository expenseRepository;
	private ExpenseTrackerFrame expenseTrackerFrame;
	private FrameFixture window;

	@Override
	protected void onSetUp() {
		mongoServer = new MongoServer(new MemoryBackend());
		InetSocketAddress serverAddress = mongoServer.bind();

		mongoClient = new MongoClient(new ServerAddress(serverAddress));

		expenseRepository = new ExpenseMongoRepository(mongoClient);

		GuiActionRunner.execute(() -> {
			expenseTrackerFrame = new ExpenseTrackerFrame();

			ExpenseController expenseController = new ExpenseController(expenseTrackerFrame, expenseRepository);

			expenseTrackerFrame.setExpenseController(expenseController);

			return expenseTrackerFrame;
		});

		window = new FrameFixture(robot(), expenseTrackerFrame);

		window.show();
	}

	@Override
	protected void onTearDown() {
		mongoClient.close();
		mongoServer.shutdownNow();
	}

	@Test
	public void shouldAddExpenseThroughUserInterface() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").enterText("2026-09-04");

		window.button(JButtonMatcher.withText("Add")).click();

		List<ExpenseRecord> expenses = expenseRepository.findAll();

		assertEquals(1, expenses.size());
		assertEquals("1", expenses.get(0).getId());
		assertEquals("Coffee", expenses.get(0).getDescription());
		assertEquals(3.50, expenses.get(0).getAmount(), 0.001);
		assertEquals("Food", expenses.get(0).getCategory());
		assertEquals(LocalDate.of(2026, 9, 4), expenses.get(0).getDate());

		window.table("expenseTable").requireRowCount(1);
	}
}