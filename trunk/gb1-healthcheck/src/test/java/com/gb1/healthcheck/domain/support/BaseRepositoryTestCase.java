package com.gb1.healthcheck.domain.support;

import org.springframework.test.context.ContextConfiguration;

import com.gb1.commons.dataaccess.AbstractInMemoryPersistenceTestCase;

@ContextConfiguration(locations = { "classpath:applicationContext-tests.xml" })
public abstract class BaseRepositoryTestCase extends AbstractInMemoryPersistenceTestCase {
	private String[] SQL_SCRIPT_RESOURCE_PATHS = { "classpath:unitTestsData.sql" };

	@Override
	protected String[] getSqlScriptResourcePaths() {
		return SQL_SCRIPT_RESOURCE_PATHS;
	}
}
