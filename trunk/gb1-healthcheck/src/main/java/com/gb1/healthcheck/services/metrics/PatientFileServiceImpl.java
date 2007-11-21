package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PatientFileRepository;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

public class PatientFileServiceImpl implements PatientFileService {
	private UserRepository userRepo;
	private PatientFileRepository patientFileRepo;

	public PatientFileServiceImpl() {
	}

	@Transactional(rollbackFor = { RuntimeException.class })
	public void setIntestinalState(Long patientId, Date instant, GastricState state) {
		User patient = userRepo.loadUser(patientId);
		PatientFile metrics = patientFileRepo.loadPatientFileFor(patient);
		metrics.setGastricState(instant, state);
	}

	@Transactional(readOnly = true)
	public PatientFile loadPatientFile(Long patientId) {
		User patient = userRepo.loadUser(patientId);
		PatientFile file = patientFileRepo.loadPatientFileFor(patient);

		return file;
	}

	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public void setPatientFileRepository(PatientFileRepository patientFileRepo) {
		this.patientFileRepo = patientFileRepo;
	}
}
