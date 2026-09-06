package com.moein.expensetracker.app;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Test;

import com.moein.expensetracker.repository.ExpenseRepository;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class ExpenseTrackerApplicationTest {

	@After
	public void clearMongoProperties() {
		System.clearProperty("mongodb.host");
		System.clearProperty("mongodb.port");
	}

	@Test
	public void shouldCreateApplicationWithRepository() {
		ExpenseRepository expenseRepository = mock(ExpenseRepository.class);

		ExpenseTrackerApplication application = new ExpenseTrackerApplication(expenseRepository);

		assertNotNull(application);
	}

	@Test
	public void shouldStartApplicationWithInjectedRepository() throws InvocationTargetException, InterruptedException {
		ExpenseRepository expenseRepository = mock(ExpenseRepository.class);

		ExpenseTrackerApplication application = new ExpenseTrackerApplication(expenseRepository);

		application.start();

		SwingUtilities.invokeAndWait(() -> {
		});

		verify(expenseRepository).findAll();
	}

	@Test
	public void shouldCreateApplicationWithDefaultConstructor() {
		ExpenseTrackerApplication application = new ExpenseTrackerApplication();

		assertNotNull(application);
	}

	@Test
	public void shouldRunMainMethod() throws Exception {
		MongoServer server = new MongoServer(new MemoryBackend());
		InetSocketAddress serverAddress = server.bind();

		System.setProperty("mongodb.host", serverAddress.getHostString());
		System.setProperty("mongodb.port", String.valueOf(serverAddress.getPort()));

		ExpenseTrackerApplication.main(new String[0]);

		SwingUtilities.invokeAndWait(() -> {
		});

		assertNotNull(server);

		server.shutdownNow();
	}
}