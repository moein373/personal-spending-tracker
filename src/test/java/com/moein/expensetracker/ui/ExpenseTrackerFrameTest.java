package com.moein.expensetracker.ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
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
}