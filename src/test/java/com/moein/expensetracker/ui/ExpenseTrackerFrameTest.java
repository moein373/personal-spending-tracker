package com.moein.expensetracker.ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

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
}