package com.moein.expensetracker.repository.mongo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
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
		expenseCollection.insertOne(new Document().append("id", expense.getId())
				.append("description", expense.getDescription()).append("amount", expense.getAmount())
				.append("category", expense.getCategory()).append("date", expense.getDate().toString()));
	}

	@Override
	public List<ExpenseRecord> findAll() {
		return StreamSupport.stream(expenseCollection.find().spliterator(), false).map(this::fromDocumentToExpense)
				.collect(Collectors.toList());
	}

	@Override
	public ExpenseRecord findById(String id) {
		Document document = expenseCollection.find(Filters.eq("id", id)).first();

		if (document != null) {
			return fromDocumentToExpense(document);
		}

		return null;
	}

	@Override
	public void delete(String id) {
		expenseCollection.deleteOne(Filters.eq("id", id));
	}

	@Override
	public void update(ExpenseRecord expense) {
		expenseCollection.replaceOne(Filters.eq("id", expense.getId()),
				new Document().append("id", expense.getId()).append("description", expense.getDescription())
						.append("amount", expense.getAmount()).append("category", expense.getCategory())
						.append("date", expense.getDate().toString()));
	}

	private ExpenseRecord fromDocumentToExpense(Document document) {
		return new ExpenseRecord(document.getString("id"), document.getString("description"),
				document.getDouble("amount"), document.getString("category"),
				LocalDate.parse(document.getString("date")));
	}
}