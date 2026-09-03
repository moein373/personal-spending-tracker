package com.moein.expensetracker.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

	private JTextField idField;
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
		idField = new JTextField();
		idField.setName("idTextBox");

		descriptionField = new JTextField();
		descriptionField.setName("descriptionTextBox");

		amountField = new JTextField();
		amountField.setName("amountTextBox");

		categoryField = new JTextField();
		categoryField.setName("categoryTextBox");

		dateField = new JTextField();
		dateField.setName("dateTextBox");

		addButton = new JButton("Add");
		addButton.setEnabled(false);

		updateButton = new JButton("Update");
		deleteButton = new JButton("Delete");

		KeyAdapter addButtonEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				addButton.setEnabled(!idField.getText().trim().isEmpty() && !descriptionField.getText().trim().isEmpty()
						&& !amountField.getText().trim().isEmpty() && !categoryField.getText().trim().isEmpty()
						&& !dateField.getText().trim().isEmpty());
			}
		};

		idField.addKeyListener(addButtonEnabler);
		descriptionField.addKeyListener(addButtonEnabler);
		amountField.addKeyListener(addButtonEnabler);
		categoryField.addKeyListener(addButtonEnabler);
		dateField.addKeyListener(addButtonEnabler);

		tableModel = new DefaultTableModel(new Object[] { "ID", "Description", "Amount", "Category", "Date" }, 0);

		expenseTable = new JTable(tableModel);
		expenseTable.setName("expenseTable");
	}

	private void createLayout() {
		JPanel formPanel = new JPanel(new GridLayout(5, 2));

		formPanel.add(new JLabel("ID"));
		formPanel.add(idField);

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