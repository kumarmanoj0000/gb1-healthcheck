package com.gb1.healthcheck.web.metrics;

import java.util.Date;
import java.util.List;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PunctualGastricState;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesAction {
	private PatientFileService patientFileService;

	public ManageGastricStatesAction() {
	}

	public String show() {
		return Action.SUCCESS;
	}

	public List<PunctualGastricState> loadGastricStatesFor(Long patientId, Date date) {
		PatientFile file = patientFileService.loadPatientFile(patientId);
		List<PunctualGastricState> states = file.getGastricStatesFor(date);

		return states;
	}

	public void savePatientGastricState(Long patientId, Date instant, GastricState state) {
		patientFileService.savePatientGastricState(patientId, instant, state);
	}

	public void setPatientFileService(PatientFileService svc) {
		this.patientFileService = svc;
	}
}
