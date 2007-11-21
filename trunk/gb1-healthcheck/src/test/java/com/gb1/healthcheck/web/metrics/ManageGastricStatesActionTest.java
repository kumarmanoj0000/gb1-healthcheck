package com.gb1.healthcheck.web.metrics;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesActionTest extends TestCase {
	public void testLoadPatientFile() {
		final User patient = Users.lg();
		PatientFile file = new PatientFile(patient);

		PatientFileService svc = EasyMock.createMock(PatientFileService.class);
		EasyMock.expect(svc.loadPatientFile(patient.getId())).andReturn(file);
		EasyMock.replay(svc);

		ManageGastricStatesAction action = new ManageGastricStatesAction() {
			@Override
			protected User getRequester() {
				return patient;
			}
		};
		action.setPatientFileService(svc);
		String result = action.show();

		assertEquals(Action.SUCCESS, result);
		assertEquals(file, action.getPatientFile());
	}
}
