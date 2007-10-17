package com.gb1.healthcheck.domain.support;

import com.gb1.commons.dataaccess.AbstractInMemoryPersistenceTestCase;

public abstract class BaseRepositoryTestCase extends AbstractInMemoryPersistenceTestCase {
	private String[] CONFIG_LOCATIONS = { "classpath:applicationContext-tests.xml" };
	private String[] SQL_SCRIPT_RESOURCE_PATHS = { "classpath:unitTestsData.sql" };

	@Override
	protected String[] getConfigLocations() {
		return CONFIG_LOCATIONS;
	}

	@Override
	protected String[] getSqlScriptResourcePaths() {
		return SQL_SCRIPT_RESOURCE_PATHS;
	}
}
