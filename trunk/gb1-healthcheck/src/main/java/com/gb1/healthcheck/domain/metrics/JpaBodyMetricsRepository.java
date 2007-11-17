package com.gb1.healthcheck.domain.metrics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gb1.healthcheck.domain.users.User;

public class JpaBodyMetricsRepository implements BodyMetricsRepository {
	private EntityManager entityManager;

	public JpaBodyMetricsRepository() {
	}

	public BodyMetrics loadBodyMetricsFor(User patient) {
		return null;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
