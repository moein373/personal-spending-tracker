package com.moein.expensetracker.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ExpenseTrackerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField descriptionField;
	private JTextField amountField;
	private JTextField categoryField;
	private JTextField dateField;

	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;

	private JTable expenseTable;
	private DefaultTableModel tableModel;

	public ExpenseTrackerFrame() {
		setTitle("Personal Spending Tracker");
		setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		createComponents();
		createLayout();
	}

	private void createComponents() {
		descriptionField = new JTextField();
		amountField = new JTextField();
		categoryField = new JTextField();
		dateField = new JTextField();

		addButton = new JButton("Add");
		addButton.setEnabled(false);
		updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");

		tableModel = new DefaultTableModel(new Object[] { "ID", "Description", "Amount", "Category", "Date" }, 0);

		expenseTable = new JTable(tableModel);
	}

	private void createLayout() {
		JPanel formPanel = new JPanel(new GridLayout(4, 2));

		formPanel.add(new JLabel("Description"));
		formPanel.add(descriptionField);

		formPanel.add(new JLabel("Amount"));
		formPanel.add(amountField);

		formPanel.add(new JLabel("Category"));
		formPanel.add(categoryField);

		formPanel.add(new JLabel("Date"));
		formPanel.add(dateField);

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(addButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);

		JPanel topPanel = new JPanel(new BorderLayout());

		topPanel.add(formPanel, BorderLayout.CENTER);
		topPanel.add(buttonPanel, BorderLayout.SOUTH);

		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(expenseTable), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ExpenseTrackerFrame frame = new ExpenseTrackerFrame();
			frame.setVisible(true);
		});
	}
}