package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;

public interface PatientFileService {
	void savePatientGastricState(Long patientId, Date instant, GastricState state);

	PatientFile findPatientFile(Long patientId);
}
