package com.gb1.healthcheck.domain.metrics;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	public void testGetGastricState() {
		Date now = new Date();
		Date tomorrow = DateUtils.addDays(now, 1);
		Date yesterday = DateUtils.addDays(now, -1);

		PatientFile file = new PatientFile(Users.gb());
		file.setGastricState(now, GastricState.NORMAL);
		file.setGastricState(tomorrow, GastricState.SLIGHTLY_BLOATED);
		file.setGastricState(yesterday, GastricState.BLOATED);

		assertEquals(GastricState.NORMAL, file.getGastricState(now));
		assertEquals(GastricState.SLIGHTLY_BLOATED, file.getGastricState(tomorrow));
		assertEquals(GastricState.BLOATED, file.getGastricState(yesterday));
	}

	public void testGetGastricStatesOnDay() throws ParseException {
		Date base = DateUtils.parseDate("2007-10-16 20:00:00",
				new String[] { "yyyy-MM-dd hh:mm:ss" });
		Date baseMinusOneHour = DateUtils.addHours(base, -1);
		Date baseMinusOneDay = DateUtils.addDays(base, 1);
		Date basePlusOneDay = DateUtils.addDays(base, -1);

		PatientFile file = new PatientFile(Users.gb());
		file.setGastricState(baseMinusOneDay, GastricState.CRISIS);
		file.setGastricState(baseMinusOneHour, GastricState.SLIGHTLY_BLOATED);
		file.setGastricState(base, GastricState.NORMAL);
		file.setGastricState(basePlusOneDay, GastricState.BLOATED);

		List<PunctualGastricState> states = file.getGastricStatesFor(base);
		assertEquals(2, states.size());
		assertEquals(GastricState.SLIGHTLY_BLOATED, states.get(0).getState());
		assertEquals(GastricState.NORMAL, states.get(1).getState());
	}

	public void testSetGastricState() {
		Date now = new Date();
		Date truncatedNow = DateUtils.truncate(now, Calendar.MINUTE);

		PatientFile file = new PatientFile(Users.gb());
		file.setGastricState(now, GastricState.NORMAL);

		assertEquals(GastricState.NORMAL, file.getGastricState(now));
		assertEquals(GastricState.NORMAL, file.getGastricState(truncatedNow));
	}
}
