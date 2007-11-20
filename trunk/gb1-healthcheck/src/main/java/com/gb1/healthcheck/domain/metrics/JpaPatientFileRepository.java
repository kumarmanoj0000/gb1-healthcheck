package com.gb1.healthcheck.domain.metrics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gb1.healthcheck.domain.users.User;

public class JpaPatientFileRepository implements PatientFileRepository {
	private EntityManager entityManager;

	public JpaPatientFileRepository() {
	}

	@SuppressWarnings("unchecked")
	public PatientFile loadPatientFileFor(User patient) {
		PatientFile file;
		List<PatientFile> files = entityManager.createQuery(
				"select pf from PatientFile pf where pf.patient = ?1").setParameter(1, patient)
				.getResultList();

		if (files.isEmpty()) {
			file = new PatientFile(patient);
			entityManager.persist(file);
		}
		else {
			file = files.get(0);
		}

		return file;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
