package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PatientFileRepository;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

@Service("patientFileService")
@Transactional(rollbackFor = { RuntimeException.class })
public class PatientFileServiceImpl implements PatientFileService {
	@Resource
	protected UserRepository userRepo;

	@Resource
	protected PatientFileRepository patientFileRepo;

	public PatientFileServiceImpl() {
	}

	public void savePatientGastricState(Long patientId, Date instant, GastricState state) {
		User patient = userRepo.findUser(patientId);
		PatientFile metrics = patientFileRepo.findPatientFile(patient);
		metrics.setGastricState(instant, state);
	}

	@Transactional(readOnly = true)
	public PatientFile loadPatientFile(Long patientId) {
		User patient = userRepo.findUser(patientId);
		return patientFileRepo.findPatientFile(patient);
	}
}
