package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PatientFileRepository;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;
import com.gb1.healthcheck.domain.users.Users;

public class PatientFileServiceImplTest extends TestCase {
	public void testSetIntestinalState() {
		User patient = Users.lg();
		Date now = new Date();
		PatientFile metrics = new PatientFile(patient);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(patient.getId())).andReturn(patient);
		EasyMock.replay(userRepo);

		PatientFileRepository metricsRepo = EasyMock.createMock(PatientFileRepository.class);
		EasyMock.expect(metricsRepo.loadPatientFileFor(patient)).andReturn(metrics);
		EasyMock.replay(metricsRepo);

		PatientFileServiceImpl svc = new PatientFileServiceImpl();
		svc.setUserRepository(userRepo);
		svc.setPatientFileRepository(metricsRepo);
		svc.savePatientGastricState(patient.getId(), now, GastricState.NORMAL);

		assertEquals(GastricState.NORMAL, metrics.getGastricState(now));
	}

	public void testLoadPatientFile() {
		User patient = Users.lg();
		PatientFile file = new PatientFile(patient);

		UserRepository userRepo = EasyMock.createMock(UserRepository.class);
		EasyMock.expect(userRepo.loadUser(patient.getId())).andReturn(patient);
		EasyMock.replay(userRepo);

		PatientFileRepository repo = EasyMock.createMock(PatientFileRepository.class);
		EasyMock.expect(repo.loadPatientFileFor(patient)).andReturn(file);
		EasyMock.replay(repo);

		PatientFileServiceImpl svc = new PatientFileServiceImpl();
		svc.setPatientFileRepository(repo);
		svc.setUserRepository(userRepo);

		assertEquals(patient, svc.loadPatientFile(patient.getId()).getPatient());
	}
}