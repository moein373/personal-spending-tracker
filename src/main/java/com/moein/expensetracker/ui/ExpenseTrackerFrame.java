package com.moein.expensetracker.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.model.ExpenseRecord;

public class ExpenseTrackerFrame extends JFrame implements ExpenseView {

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

	private ExpenseController expenseController;

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

		addButton.addActionListener(e -> expenseController.addExpense(new ExpenseRecord(idField.getText(),
				descriptionField.getText(), Double.parseDouble(amountField.getText()), categoryField.getText(),
				java.time.LocalDate.parse(dateField.getText()))));

		tableModel = new DefaultTableModel(new Object[] { "ID", "Description", "Amount", "Category", "Date" }, 0);

		expenseTable = new JTable(tableModel);
		expenseTable.setName("expenseTable");
		expenseTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = expenseTable.getSelectedRow();

				if (selectedRow >= 0) {
					idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
					descriptionField.setText(tableModel.getValueAt(selectedRow, 1).toString());
					amountField.setText(tableModel.getValueAt(selectedRow, 2).toString());
					categoryField.setText(tableModel.getValueAt(selectedRow, 3).toString());
					dateField.setText(tableModel.getValueAt(selectedRow, 4).toString());
				}
			}
		});

		updateButton.addActionListener(e -> {
			ExpenseRecord expense = new ExpenseRecord(idField.getText(), descriptionField.getText(),
					Double.parseDouble(amountField.getText()), categoryField.getText(),
					java.time.LocalDate.parse(dateField.getText()));

			expenseController.updateExpense(expense);
		});

		deleteButton.addActionListener(e -> {
			int selectedRow = expenseTable.getSelectedRow();

			if (selectedRow >= 0) {
				ExpenseRecord expense = new ExpenseRecord(tableModel.getValueAt(selectedRow, 0).toString(),
						tableModel.getValueAt(selectedRow, 1).toString(),
						Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString()),
						tableModel.getValueAt(selectedRow, 3).toString(),
						java.time.LocalDate.parse(tableModel.getValueAt(selectedRow, 4).toString()));

				expenseController.deleteExpense(expense);
			}
		});
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

	@Override
	public void showAllExpenses(List<ExpenseRecord> expenses) {
		tableModel.setRowCount(0);

		for (ExpenseRecord expense : expenses) {
			tableModel.addRow(new Object[] { expense.getId(), expense.getDescription(), expense.getAmount(),
					expense.getCategory(), expense.getDate() });
		}
	}

	@Override
	public void expenseAdded(ExpenseRecord expense) {
		tableModel.addRow(new Object[] { expense.getId(), expense.getDescription(), expense.getAmount(),
				expense.getCategory(), expense.getDate() });
	}

	@Override
	public void expenseRemoved(ExpenseRecord expense) {
		for (int row = 0; row < tableModel.getRowCount(); row++) {
			Object idValue = tableModel.getValueAt(row, 0);

			if (expense.getId().equals(idValue)) {
				tableModel.removeRow(row);
				break;
			}
		}
	}

	@Override
	public void expenseUpdated(ExpenseRecord expense) {
		for (int row = 0; row < tableModel.getRowCount(); row++) {
			Object idValue = tableModel.getValueAt(row, 0);

			if (expense.getId().equals(idValue)) {
				tableModel.setValueAt(expense.getDescription(), row, 1);
				tableModel.setValueAt(expense.getAmount(), row, 2);
				tableModel.setValueAt(expense.getCategory(), row, 3);
				tableModel.setValueAt(expense.getDate(), row, 4);
				break;
			}
		}
	}

	public void setExpenseController(ExpenseController expenseController) {
		this.expenseController = expenseController;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ExpenseTrackerFrame frame = new ExpenseTrackerFrame();
			frame.setVisible(true);
		});
	}
}