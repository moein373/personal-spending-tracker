package com.moein.expensetracker.repository.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.moein.expensetracker.model.ExpenseRecord;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class ExpenseMongoRepositoryTest {

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient client;
	private ExpenseMongoRepository expenseRepository;
	private MongoCollection<Document> expenseCollection;

	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());

		serverAddress = server.bind();
	}

	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}

	@Before
	public void setup() {
		client = new MongoClient(new ServerAddress(serverAddress));

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
	public void saveShouldStoreExpenseInDatabase() {
		ExpenseRecord expense = new ExpenseRecord("1", "Lunch", 12.50, "Food", LocalDate.of(2026, 8, 31));

		expenseRepository.save(expense);

		Document document = expenseCollection.find().first();

		assertNotNull(document);
		assertEquals("1", document.getString("id"));
		assertEquals("Lunch", document.getString("description"));
		assertEquals(12.50, document.getDouble("amount"), 0.0);
		assertEquals("Food", document.getString("category"));
		assertEquals("2026-08-31", document.getString("date"));
	}

	@Test
	public void findAllShouldReturnAllExpensesFromDatabase() {
		expenseCollection.insertOne(new Document().append("id", "1").append("description", "Lunch")
				.append("amount", 12.50).append("category", "Food").append("date", "2026-08-31"));

		expenseCollection.insertOne(new Document().append("id", "2").append("description", "Bus").append("amount", 3.00)
				.append("category", "Transport").append("date", "2026-08-31"));

		List<ExpenseRecord> expenses = expenseRepository.findAll();

		assertEquals(2, expenses.size());

		assertEquals("1", expenses.get(0).getId());
		assertEquals("Lunch", expenses.get(0).getDescription());
		assertEquals(12.50, expenses.get(0).getAmount(), 0.0);
		assertEquals("Food", expenses.get(0).getCategory());
		assertEquals(LocalDate.of(2026, 8, 31), expenses.get(0).getDate());

		assertEquals("2", expenses.get(1).getId());
		assertEquals("Bus", expenses.get(1).getDescription());
		assertEquals(3.00, expenses.get(1).getAmount(), 0.0);
		assertEquals("Transport", expenses.get(1).getCategory());
		assertEquals(LocalDate.of(2026, 8, 31), expenses.get(1).getDate());
	}

	@Test
	public void findByIdShouldReturnExpenseWhenExpenseExists() {
		expenseCollection.insertOne(new Document().append("id", "1").append("description", "Lunch")
				.append("amount", 12.50).append("category", "Food").append("date", "2026-08-31"));

		ExpenseRecord expense = expenseRepository.findById("1");

		assertNotNull(expense);
		assertEquals("1", expense.getId());
		assertEquals("Lunch", expense.getDescription());
		assertEquals(12.50, expense.getAmount(), 0.0);
		assertEquals("Food", expense.getCategory());
		assertEquals(LocalDate.of(2026, 8, 31), expense.getDate());
	}

	@Test
	public void findByIdShouldReturnNullWhenExpenseDoesNotExist() {
		ExpenseRecord expense = expenseRepository.findById("999");

		assertEquals(null, expense);
	}
}