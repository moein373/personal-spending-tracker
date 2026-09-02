package com.moein.expensetracker.repository.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.moein.expensetracker.model.ExpenseRecord;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ExpenseMongoRepositoryIT {

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

	private MongoClient client;
	private ExpenseMongoRepository expenseRepository;
	private MongoCollection<Document> expenseCollection;

	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(mongo.getContainerIpAddress(), mongo.getMappedPort(27017)));

		expenseRepository = new ExpenseMongoRepository(client);

		MongoDatabase database = client.getDatabase(ExpenseMongoRepository.DATABASE_NAME);

		database.drop();

		expenseCollection = database.getCollection(ExpenseMongoRepository.COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void shouldConnectToMongoDBContainer() {
		assertNotNull(expenseCollection);
	}

	@Test
	public void shouldSaveExpenseToMongoDB() {
		ExpenseRecord expense = new ExpenseRecord("1", "Coffee", 3.50, "Food", LocalDate.of(2026, 9, 2));

		expenseRepository.save(expense);

		Document document = expenseCollection.find(new Document("id", "1")).first();

		assertNotNull(document);
		assertEquals("Coffee", document.getString("description"));
		assertEquals(3.50, document.getDouble("amount"), 0.0);
		assertEquals("Food", document.getString("category"));
		assertEquals("2026-09-02", document.getString("date"));
	}

	@Test
	public void shouldFindAllExpensesFromMongoDB() {
		expenseCollection.insertOne(new Document("id", "2").append("description", "Lunch").append("amount", 12.50)
				.append("category", "Food").append("date", "2026-09-02"));

		expenseCollection.insertOne(new Document("id", "3").append("description", "Taxi").append("amount", 15.00)
				.append("category", "Transport").append("date", "2026-09-02"));

		List<ExpenseRecord> expenses = expenseRepository.findAll();

		assertEquals(2, expenses.size());
		assertEquals("2", expenses.get(0).getId());
		assertEquals("Lunch", expenses.get(0).getDescription());
		assertEquals("3", expenses.get(1).getId());
		assertEquals("Taxi", expenses.get(1).getDescription());
	}

	@Test
	public void shouldFindExpenseByIdFromMongoDB() {
		expenseCollection.insertOne(new Document("id", "4").append("description", "Gym Membership")
				.append("amount", 30.00).append("category", "Fitness").append("date", "2026-09-02"));

		ExpenseRecord expense = expenseRepository.findById("4");

		assertNotNull(expense);
		assertEquals("4", expense.getId());
		assertEquals("Gym Membership", expense.getDescription());
		assertEquals(30.00, expense.getAmount(), 0.0);
		assertEquals("Fitness", expense.getCategory());
		assertEquals(LocalDate.of(2026, 9, 2), expense.getDate());
	}
}