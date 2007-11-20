package com.gb1.healthcheck.domain.metrics;

import com.gb1.healthcheck.domain.users.User;

public interface PatientFileRepository {
	PatientFile loadPatientFileFor(User patient);
}
