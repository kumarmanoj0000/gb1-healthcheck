package com.gb1.healthcheck.services.metrics;

import java.util.Date;

import com.gb1.healthcheck.domain.metrics.GastricState;

public interface PatientFileService {
	void setIntestinalState(Long patientId, Date instant, GastricState state);
}
