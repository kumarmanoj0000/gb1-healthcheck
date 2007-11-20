package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.domain.users.Users;

public class PatientFileTest extends TestCase {
	public void testEquals() {
		PatientFile tr1 = new PatientFile(Users.gb());
		PatientFile tr2 = new PatientFile(Users.gb());
		PatientFile tr3 = new PatientFile(Users.lg());

		assertTrue(tr1.equals(tr2));
		assertFalse(tr1.equals(tr3));
	}

	public void testIntestinalStatuses() {
		Date now = new Date();
		Date tomorrow = DateUtils.addDays(now, 1);
		Date yesterday = DateUtils.addDays(now, -1);

		PatientFile tr = new PatientFile(Users.gb());
		tr.setIntestinalState(now, IntestinalState.NORMAL);
		tr.setIntestinalState(tomorrow, IntestinalState.SLIGHTLY_BLOATED);
		tr.setIntestinalState(yesterday, IntestinalState.BLOATED);

		assertEquals(IntestinalState.NORMAL, tr.getIntestinalState(now));
		assertEquals(IntestinalState.SLIGHTLY_BLOATED, tr.getIntestinalState(tomorrow));
		assertEquals(IntestinalState.BLOATED, tr.getIntestinalState(yesterday));
	}
}
