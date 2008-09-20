package com.gb1.commons.test;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * Base class for persistence test cases. Before starting the first transaction, it populates the
 * database using SQL scripts provided by the sub-classes. As is the case with all classes
 * leveraging Spring's TestContext framework, all test cases inheriting
 * AbstractTransactionJUnit4SpringContextTests, and therefore this class, will have their test
 * methods executed within the context of a transaction, which will be automatically started before
 * starting and rolled back after completing.
 * 
 * @author Guillaume Bilodeau
 */
@ContextConfiguration(locations = { "classpath:applicationContext-tests.xml" })
public abstract class AbstractInMemoryPersistenceTestCase extends
		AbstractTransactionalJUnit4SpringContextTests {
	private static final Logger logger = Logger
			.getLogger(AbstractInMemoryPersistenceTestCase.class);

	private static final String[] SQL_SCRIPT_RESOURCE_PATHS = { "classpath:unitTestsData.sql" };
	private static final boolean CONTINUE_SQL_SCRIPT_ON_ERROR = true;

	/**
	 * Indicates whether the database already populated or not.
	 */
	private static boolean databasePopulated = false;

	/**
	 * Returns whether the test database has already been populated or not.
	 * 
	 * @return true is the database was populated; false otherwise
	 */
	private static boolean isDatabasePopulated() {
		return databasePopulated;
	}

	/**
	 * Callback to inform all test cases that the test database has been populated.
	 */
	private static void databasePopulated() {
		databasePopulated = true;
	}

	@BeforeTransaction
	public void populateDatabaseIfNecessary() {
		if (!AbstractInMemoryPersistenceTestCase.isDatabasePopulated()) {
			populateDatabase();
			AbstractInMemoryPersistenceTestCase.databasePopulated();
		}
	}

	/**
	 * Populates the test database with the required test data.
	 */
	private void populateDatabase() {
		logger.info("Populating test database...");

		for (String sqlScriptPath : SQL_SCRIPT_RESOURCE_PATHS) {
			executeSqlScript(sqlScriptPath, CONTINUE_SQL_SCRIPT_ON_ERROR);
		}

		logger.info("Test database populated");
	}
}
