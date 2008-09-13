package com.gb1.healthcheck.domain.metrics;

import static junit.framework.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;

public class JpaPatientFileRepositoryTest extends BaseRepositoryTestCase {
	@Resource
	private PatientFileRepository repo;

	@Test
	public void testLoadPatientFileNewPatient() {
		// this user doesn't have any patient file in the test database yet
		User patient = Users.gb();
		PatientFile metrics = repo.loadPatientFileFor(patient);
		assertEquals(patient, metrics.getPatient());
	}

	@Test
	public void testLoadPatientFileExistingPatient() {
		// this user already has a patient file saved in the database
		User patient = Users.lg();
		PatientFile metrics = repo.loadPatientFileFor(patient);
		assertEquals(patient, metrics.getPatient());
	}
}
