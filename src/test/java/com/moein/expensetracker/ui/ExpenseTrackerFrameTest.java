package com.moein.expensetracker.ui;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.controller.ExpenseController;

@RunWith(GUITestRunner.class)
public class ExpenseTrackerFrameTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private ExpenseTrackerFrame expenseTrackerFrame;

	@Override
	protected void onSetUp() {
		GuiActionRunner.execute(() -> {
			expenseTrackerFrame = new ExpenseTrackerFrame();
			return expenseTrackerFrame;
		});

		window = new FrameFixture(robot(), expenseTrackerFrame);
		window.show();
	}

	@Test
	public void shouldCreateExpenseTrackerFrame() {
	}

	@Test
	public void addButtonShouldBeDisabledByDefault() {
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}

	@Test
	public void addButtonShouldBeEnabledWhenAllFieldsAreFilled() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").enterText("2026-09-03");

		window.button(JButtonMatcher.withText("Add")).requireEnabled();
	}

	@Test
	public void addButtonShouldRemainDisabledWhenAnyFieldIsBlank() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText(" ");
		window.textBox("dateTextBox").enterText("2026-09-03");

		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}

	@Test
	public void shouldAcceptExpenseController() {
		ExpenseController expenseController = mock(ExpenseController.class);

		expenseTrackerFrame.setExpenseController(expenseController);
	}

	@Test
	public void addButtonShouldDelegateExpenseToController() {
		ExpenseController expenseController = mock(ExpenseController.class);
		expenseTrackerFrame.setExpenseController(expenseController);

		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").enterText("2026-09-03");

		window.button(JButtonMatcher.withText("Add")).click();

		verify(expenseController).addExpense(argThat(expense -> "1".equals(expense.getId())
				&& "Coffee".equals(expense.getDescription()) && Double.compare(3.50, expense.getAmount()) == 0
				&& "Food".equals(expense.getCategory()) && LocalDate.of(2026, 9, 3).equals(expense.getDate())));
	}

	@Test
	public void expenseAddedShouldAddExpenseToTable() {
		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.expenseAdded(expense));

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable").requireContents(new String[][] { { "1", "Coffee", "3.5", "Food", "2026-09-03" } });
	}

}