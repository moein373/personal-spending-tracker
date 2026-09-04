package com.moein.expensetracker.app;

import javax.swing.SwingUtilities;

import com.mongodb.MongoClient;
import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.repository.mongo.ExpenseMongoRepository;
import com.moein.expensetracker.ui.ExpenseTrackerFrame;

public class ExpenseTrackerApplication {

	private final ExpenseRepository expenseRepository;

	public ExpenseTrackerApplication() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		this.expenseRepository = new ExpenseMongoRepository(mongoClient);
	}

	public ExpenseTrackerApplication(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public static void main(String[] args) {
		new ExpenseTrackerApplication().start();
	}

	public void start() {
		SwingUtilities.invokeLater(() -> {
			ExpenseTrackerFrame frame = new ExpenseTrackerFrame();

			ExpenseController expenseController = new ExpenseController(frame, expenseRepository);

			frame.setExpenseController(expenseController);

			expenseController.loadAllExpenses();

			frame.setVisible(true);
		});
	}
}