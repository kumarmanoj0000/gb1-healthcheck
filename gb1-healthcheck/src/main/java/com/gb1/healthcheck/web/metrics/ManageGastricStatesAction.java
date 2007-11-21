package com.gb1.healthcheck.web.metrics;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesAction implements ServletRequestAware {
	private HttpServletRequest servletRequest;
	private PatientFileService patientFileService;
	private PatientFile patientFile;

	public ManageGastricStatesAction() {
	}

	public String show() {
		patientFile = patientFileService.loadPatientFile(getRequester().getId());
		return Action.SUCCESS;
	}

	protected User getRequester() {
		User patient = HttpRequestUtils.getUser(servletRequest);
		return patient;
	}

	public PatientFile getPatientFile() {
		return patientFile;
	}

	public void setPatientFileService(PatientFileService patientFileService) {
		this.patientFileService = patientFileService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.servletRequest = request;
	}
}
