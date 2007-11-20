package com.gb1.healthcheck.domain.metrics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gb1.healthcheck.domain.users.User;

public class JpaPatientFileRepository implements PatientFileRepository {
	private EntityManager entityManager;

	public JpaPatientFileRepository() {
	}

	public PatientFile loadPatientFileFor(User patient) {
		return null;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
