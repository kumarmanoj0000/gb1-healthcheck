package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.metrics.BodyMetrics;
import com.gb1.healthcheck.domain.metrics.BodyMetricsRepository;
import com.gb1.healthcheck.domain.metrics.IntestinalState;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class BodyMetricsServiceImplTest extends TestCase {
	public void testSetIntestinalState() {
		User patient = Users.lg();
		Date now = new Date();
		BodyMetrics metrics = new BodyMetrics(patient);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(patient.getId())).andReturn(patient);
		EasyMock.replay(userRepo);

		BodyMetricsRepository metricsRepo = EasyMock.createMock(BodyMetricsRepository.class);
		EasyMock.expect(metricsRepo.loadBodyMetricsFor(patient)).andReturn(metrics);
		EasyMock.replay(metricsRepo);

		BodyMetricsServiceImpl svc = new BodyMetricsServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setBodyMetricsRepository(metricsRepo);
		svc.setIntestinalState(patient.getId(), now, IntestinalState.NORMAL);

		assertEquals(IntestinalState.NORMAL, metrics.getIntestinalState(now));
	}
}
