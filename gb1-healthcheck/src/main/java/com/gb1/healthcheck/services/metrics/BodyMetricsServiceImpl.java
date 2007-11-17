package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.metrics.BodyMetrics;
import com.gb1.healthcheck.domain.metrics.BodyMetricsRepository;
import com.gb1.healthcheck.domain.metrics.IntestinalState;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

public class BodyMetricsServiceImpl implements BodyMetricsService {
	private UserRepository userRepo;
	private BodyMetricsRepository bodyMetricsRepo;

	public BodyMetricsServiceImpl() {
	}

	@Transactional(rollbackFor = { RuntimeException.class })
	public void setIntestinalState(Long patientId, Date instant, IntestinalState state) {
		User patient = userRepo.loadUser(patientId);
		BodyMetrics metrics = bodyMetricsRepo.loadBodyMetricsFor(patient);
		metrics.setIntestinalState(instant, state);
	}

	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public void setBodyMetricsRepository(BodyMetricsRepository bodyMetricsRepo) {
		this.bodyMetricsRepo = bodyMetricsRepo;
	}
}
