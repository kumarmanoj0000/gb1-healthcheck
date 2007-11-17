package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import com.gb1.healthcheck.domain.metrics.IntestinalState;

public interface BodyMetricsService {
	void setIntestinalState(Long patientId, Date instant, IntestinalState state);
}
