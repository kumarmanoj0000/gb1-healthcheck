package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.domain.users.Users;

public class BodyMetricsTest extends TestCase {
	public void testEquals() {
		BodyMetrics tr1 = new BodyMetrics(Users.gb());
		BodyMetrics tr2 = new BodyMetrics(Users.gb());
		BodyMetrics tr3 = new BodyMetrics(Users.lg());

		assertTrue(tr1.equals(tr2));
		assertFalse(tr1.equals(tr3));
	}

	public void testIntestinalStatuses() {
		Date now = new Date();
		Date tomorrow = DateUtils.addDays(now, 1);
		Date yesterday = DateUtils.addDays(now, -1);

		BodyMetrics tr = new BodyMetrics(Users.gb());
		tr.setIntestinalState(now, IntestinalState.NORMAL);
		tr.setIntestinalState(tomorrow, IntestinalState.SLIGHTLY_BLOATED);
		tr.setIntestinalState(yesterday, IntestinalState.BLOATED);

		assertEquals(IntestinalState.NORMAL, tr.getIntestinalState(now));
		assertEquals(IntestinalState.SLIGHTLY_BLOATED, tr.getIntestinalState(tomorrow));
		assertEquals(IntestinalState.BLOATED, tr.getIntestinalState(yesterday));
	}
}
