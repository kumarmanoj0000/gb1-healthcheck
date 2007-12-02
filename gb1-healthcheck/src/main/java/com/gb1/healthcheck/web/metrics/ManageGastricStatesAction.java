package com.gb1.healthcheck.web.metrics;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PunctualGastricState;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesAction implements ServletRequestAware {
	private HttpServletRequest request;
	private PatientFileService patientFileService;
	private User patient;

	public ManageGastricStatesAction() {
	}

	public String show() {
		patient = getRequester(request);
		return Action.SUCCESS;
	}

	protected User getRequester(HttpServletRequest request) {
		return HttpRequestUtils.getUser(request);
	}

	public List<PunctualGastricState> loadGastricStates(Long patientId, Date date) {
		PatientFile file = patientFileService.loadPatientFile(patientId);
		List<PunctualGastricState> states = file.getGastricStatesFor(date);

		return states;
	}

	public void saveGastricState(Long patientId, Date instant, GastricState state) {
		patientFileService.savePatientGastricState(patientId, instant, state);
	}

	public User getPatient() {
		return patient;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setPatientFileService(PatientFileService svc) {
		this.patientFileService = svc;
	}
}
