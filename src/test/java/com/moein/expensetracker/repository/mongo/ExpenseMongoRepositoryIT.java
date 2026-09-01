package com.moein.expensetracker.repository.mongo;

import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

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
}