package com.moein.expensetracker.repository.mongo;

import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.moein.expensetracker.model.ExpenseRecord;
import com.moein.expensetracker.repository.ExpenseRepository;

public class ExpenseMongoRepository implements ExpenseRepository {

	public static final String DATABASE_NAME = "expense_tracker";
	public static final String COLLECTION_NAME = "expenses";

	private MongoCollection<Document> expenseCollection;

	public ExpenseMongoRepository(MongoClient client) {
		expenseCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME);
	}

	@Override
	public void save(ExpenseRecord expense) {
	}

	@Override
	public List<ExpenseRecord> findAll() {
		return Collections.emptyList();
	}

	@Override
	public ExpenseRecord findById(String id) {
		return null;
	}

	@Override
	public void delete(String id) {
	}

	@Override
	public void update(ExpenseRecord expense) {
	}
}