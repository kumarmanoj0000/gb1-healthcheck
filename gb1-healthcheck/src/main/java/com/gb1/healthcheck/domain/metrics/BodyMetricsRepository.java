package com.gb1.healthcheck.domain.metrics;

import com.gb1.healthcheck.domain.users.User;

public interface BodyMetricsRepository {
	BodyMetrics loadBodyMetricsFor(User patient);
}
