package com.moein.expensetracker.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Arrays;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.model.ExpenseRecord;

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
		assertNotNull(expenseTrackerFrame);
	}

	@Test
	public void addButtonShouldBeDisabledByDefault() {
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		assertFalse(window.button(JButtonMatcher.withText("Add")).target().isEnabled());
	}

	@Test
	public void addButtonShouldBeEnabledWhenAllFieldsAreFilled() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").enterText("2026-09-03");

		window.button(JButtonMatcher.withText("Add")).requireEnabled();
		assertTrue(window.button(JButtonMatcher.withText("Add")).target().isEnabled());
	}

	@Test
	public void addButtonShouldRemainDisabledWhenAnyFieldIsBlank() {
		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText(" ");
		window.textBox("dateTextBox").enterText("2026-09-03");

		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		assertFalse(window.button(JButtonMatcher.withText("Add")).target().isEnabled());
	}

	@Test
	public void shouldAcceptExpenseController() {
		ExpenseController expenseController = mock(ExpenseController.class);

		expenseTrackerFrame.setExpenseController(expenseController);

		assertNotNull(expenseController);
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

		assertEquals(1, window.table("expenseTable").target().getRowCount());
	}

	@Test
	public void showAllExpensesShouldPopulateTable() {
		ExpenseRecord expense1 = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		ExpenseRecord expense2 = new ExpenseRecord("2", "Bus", 1.50, "Transport", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(expense1, expense2)));

		window.table("expenseTable").requireRowCount(2);

		window.table("expenseTable").requireContents(new String[][] { { "1", "Coffee", "3.5", "Food", "2026-09-03" },
				{ "2", "Bus", "1.5", "Transport", "2026-09-03" } });

		assertEquals(2, window.table("expenseTable").target().getRowCount());
	}

	@Test
	public void expenseRemovedShouldRemoveExpenseFromTable() {
		ExpenseRecord expense1 = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		ExpenseRecord expense2 = new ExpenseRecord("2", "Bus", 1.50, "Transport", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(expense1, expense2)));

		GuiActionRunner.execute(() -> expenseTrackerFrame.expenseRemoved(expense1));

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable")
				.requireContents(new String[][] { { "2", "Bus", "1.5", "Transport", "2026-09-03" } });

		assertEquals(1, window.table("expenseTable").target().getRowCount());
	}

	@Test
	public void deleteButtonShouldDelegateSelectedExpenseToController() {
		ExpenseController expenseController = mock(ExpenseController.class);
		expenseTrackerFrame.setExpenseController(expenseController);

		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(expense)));

		window.table("expenseTable").selectRows(0);

		window.button(JButtonMatcher.withText("Delete")).click();

		verify(expenseController).deleteExpense(argThat(selectedExpense -> "1".equals(selectedExpense.getId())
				&& "Coffee".equals(selectedExpense.getDescription())
				&& Double.compare(3.50, selectedExpense.getAmount()) == 0
				&& "Food".equals(selectedExpense.getCategory())
				&& LocalDate.of(2026, 9, 3).equals(selectedExpense.getDate())));
	}

	@Test
	public void expenseUpdatedShouldUpdateExpenseInTable() {
		ExpenseRecord originalExpense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		ExpenseRecord updatedExpense = new ExpenseRecord("1", "Dinner", 25.00, "Food", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(originalExpense)));

		GuiActionRunner.execute(() -> expenseTrackerFrame.expenseUpdated(updatedExpense));

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable")
				.requireContents(new String[][] { { "1", "Dinner", "25.0", "Food", "2026-09-03" } });

		assertEquals("Dinner", window.table("expenseTable").target().getValueAt(0, 1));
	}

	@Test
	public void updateButtonShouldDelegateEditedExpenseToController() {
		ExpenseController expenseController = mock(ExpenseController.class);
		expenseTrackerFrame.setExpenseController(expenseController);

		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 3));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(expense)));

		window.table("expenseTable").selectRows(0);

		window.textBox("idTextBox").setText("1");
		window.textBox("descriptionTextBox").setText("Dinner");
		window.textBox("amountTextBox").setText("25.00");
		window.textBox("categoryTextBox").setText("Food");
		window.textBox("dateTextBox").setText("2026-09-03");

		window.button(JButtonMatcher.withText("Update")).click();

		verify(expenseController).updateExpense(argThat(updatedExpense -> "1".equals(updatedExpense.getId())
				&& "Dinner".equals(updatedExpense.getDescription())
				&& Double.compare(25.00, updatedExpense.getAmount()) == 0 && "Food".equals(updatedExpense.getCategory())
				&& LocalDate.of(2026, 9, 3).equals(updatedExpense.getDate())));
	}

	@Test
	public void selectingExpenseShouldPopulateFormFields() {
		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 4));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(expense)));

		window.table("expenseTable").selectRows(0);

		window.textBox("idTextBox").requireText("1");
		window.textBox("descriptionTextBox").requireText("Coffee");
		window.textBox("amountTextBox").requireText("3.5");
		window.textBox("categoryTextBox").requireText("Food");
		window.textBox("dateTextBox").requireText("2026-09-04");

		assertEquals("Coffee", window.textBox("descriptionTextBox").target().getText());
	}

	@Test
	public void deleteButtonShouldDoNothingWhenNoExpenseIsSelected() {
		ExpenseController expenseController = mock(ExpenseController.class);
		expenseTrackerFrame.setExpenseController(expenseController);

		window.button(JButtonMatcher.withText("Delete")).click();

		verify(expenseController, never()).deleteExpense(any(ExpenseRecord.class));
	}

	@Test
	public void expenseRemovedShouldLeaveTableUnchangedWhenExpenseDoesNotExist() {
		ExpenseRecord existingExpense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 4));

		ExpenseRecord missingExpense = new ExpenseRecord("2", "Bus", 1.50, "Transport", LocalDate.of(2026, 9, 4));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(existingExpense)));

		GuiActionRunner.execute(() -> expenseTrackerFrame.expenseRemoved(missingExpense));

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable").requireContents(new String[][] { { "1", "Coffee", "3.5", "Food", "2026-09-04" } });

		assertEquals(1, window.table("expenseTable").target().getRowCount());
	}

	@Test
	public void expenseUpdatedShouldLeaveTableUnchangedWhenExpenseDoesNotExist() {
		ExpenseRecord existingExpense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 4));

		ExpenseRecord missingExpense = new ExpenseRecord("2", "Dinner", 25.00, "Food", LocalDate.of(2026, 9, 4));

		GuiActionRunner.execute(() -> expenseTrackerFrame.showAllExpenses(Arrays.asList(existingExpense)));

		GuiActionRunner.execute(() -> expenseTrackerFrame.expenseUpdated(missingExpense));

		window.table("expenseTable").requireRowCount(1);

		window.table("expenseTable").requireContents(new String[][] { { "1", "Coffee", "3.5", "Food", "2026-09-04" } });

		assertEquals("Coffee", window.table("expenseTable").target().getValueAt(0, 1));
	}

	@Test
	public void addButtonShouldRemainDisabledForEachMissingRequiredField() {
		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").enterText("2026-09-04");

		window.button(JButtonMatcher.withText("Add")).requireDisabled();

		window.textBox("idTextBox").enterText("1");
		window.textBox("descriptionTextBox").deleteText();

		window.button(JButtonMatcher.withText("Add")).requireDisabled();

		window.textBox("descriptionTextBox").enterText("Coffee");
		window.textBox("amountTextBox").deleteText();

		window.button(JButtonMatcher.withText("Add")).requireDisabled();

		window.textBox("amountTextBox").enterText("3.50");
		window.textBox("categoryTextBox").deleteText();

		window.button(JButtonMatcher.withText("Add")).requireDisabled();

		window.textBox("categoryTextBox").enterText("Food");
		window.textBox("dateTextBox").deleteText();

		window.button(JButtonMatcher.withText("Add")).requireDisabled();

		assertFalse(window.button(JButtonMatcher.withText("Add")).target().isEnabled());
	}

}