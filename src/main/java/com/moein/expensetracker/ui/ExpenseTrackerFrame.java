package com.moein.expensetracker.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ExpenseTrackerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public ExpenseTrackerFrame() {
		setTitle("Personal Spending Tracker");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ExpenseTrackerFrame frame = new ExpenseTrackerFrame();
			frame.setVisible(true);
		});
	}
}