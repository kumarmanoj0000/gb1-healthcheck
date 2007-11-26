package com.gb1.healthcheck.web.metrics;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesActionTest extends TestCase {
	private static final String[] PARSE_PATTERNS = new String[] { "yyyy-MM-dd hh:mm:ss" };

	public void testShow() {
		ManageGastricStatesAction action = new ManageGastricStatesAction();
		assertEquals(Action.SUCCESS, action.show());
	}

	public void testLoadStatesFor() throws ParseException {
		final Date today = new Date();
		final User patient = Users.lg();

		final PatientFile file = new PatientFile(patient);
		file.setGastricState(parseDateTime("2007-11-25 12:00:00"), GastricState.NORMAL);
		file.setGastricState(parseDateTime("2007-11-25 16:00:00"), GastricState.BLOATED);
		file.setGastricState(parseDateTime("2007-11-25 20:00:00"), GastricState.SLIGHTLY_BLOATED);

		PatientFileService svc = EasyMock.createMock(PatientFileService.class);
		EasyMock.expect(svc.loadPatientFile(patient.getId())).andReturn(file);
		EasyMock.replay(svc);

		ManageGastricStatesAction action = new ManageGastricStatesAction();
		action.setPatientFileService(svc);
		assertTrue(CollectionUtils.isEqualCollection(file.getGastricStatesFor(today), action
				.loadGastricStatesFor(patient.getId(), today)));
	}

	public void testSetState() {
		final Date now = new Date();
		final User patient = Users.lg();

		PatientFileService svc = EasyMock.createMock(PatientFileService.class);
		svc.savePatientGastricState(patient.getId(), now, GastricState.NORMAL);
		EasyMock.expectLastCall();
		EasyMock.replay(svc);

		ManageGastricStatesAction action = new ManageGastricStatesAction();
		action.setPatientFileService(svc);

		action.savePatientGastricState(patient.getId(), now, GastricState.NORMAL);
		EasyMock.verify(svc);
	}

	private Date parseDateTime(String text) throws ParseException {
		return DateUtils.parseDate(text, PARSE_PATTERNS);
	}
}
