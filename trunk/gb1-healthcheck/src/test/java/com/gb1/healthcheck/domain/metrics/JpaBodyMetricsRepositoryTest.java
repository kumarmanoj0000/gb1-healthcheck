package com.gb1.healthcheck.domain.metrics;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;

public class JpaBodyMetricsRepositoryTest extends BaseRepositoryTestCase {
	private BodyMetricsRepository repo;

	public void testLoadBodyMetricsNewPatient() {
		// this user doesn't have any metrics in the test database yet
		User patient = Users.gb();
		BodyMetrics metrics = repo.loadBodyMetricsFor(patient);
		assertEquals(patient, metrics.getPatient());
	}

	public void testLoadBodyMetricsExistingPatient() {
		// this user already has some metrics saved in the database
		User patient = Users.lg();
		BodyMetrics metrics = repo.loadBodyMetricsFor(patient);
		assertEquals(patient, metrics.getPatient());
	}

	public void setMetricsRepository(BodyMetricsRepository repo) {
		this.repo = repo;
	}
}
