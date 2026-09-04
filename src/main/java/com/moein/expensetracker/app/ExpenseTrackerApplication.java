package com.moein.expensetracker.app;

import javax.swing.SwingUtilities;

import com.mongodb.MongoClient;
import com.moein.expensetracker.controller.ExpenseController;
import com.moein.expensetracker.repository.mongo.ExpenseMongoRepository;
import com.moein.expensetracker.repository.ExpenseRepository;
import com.moein.expensetracker.ui.ExpenseTrackerFrame;

public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		ExpenseRepository expenseRepository = new ExpenseMongoRepository(mongoClient);

		SwingUtilities.invokeLater(() -> {
			ExpenseTrackerFrame frame = new ExpenseTrackerFrame();

			ExpenseController expenseController = new ExpenseController(frame, expenseRepository);

			frame.setExpenseController(expenseController);

			expenseController.loadAllExpenses();

			frame.setVisible(true);
		});
	}
}