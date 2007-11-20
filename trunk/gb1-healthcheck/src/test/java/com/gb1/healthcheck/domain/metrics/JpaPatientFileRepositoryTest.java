package com.gb1.healthcheck.domain.metrics;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;

public class JpaPatientFileRepositoryTest extends BaseRepositoryTestCase {
	private PatientFileRepository repo;

	public void testLoadBodyMetricsNewPatient() {
		// this user doesn't have any patient file in the test database yet
		User patient = Users.gb();
		PatientFile metrics = repo.loadPatientFileFor(patient);
		assertEquals(patient, metrics.getPatient());
	}

	public void testLoadBodyMetricsExistingPatient() {
		// this user already has a patient file saved in the database
		User patient = Users.lg();
		PatientFile metrics = repo.loadPatientFileFor(patient);
		assertEquals(patient, metrics.getPatient());
	}

	public void setPatientFileRepository(PatientFileRepository repo) {
		this.repo = repo;
	}
}
