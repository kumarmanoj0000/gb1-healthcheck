package com.gb1.healthcheck.web.metrics;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;
import com.opensymphony.xwork2.Action;

public class ManageGastricStatesAction implements ServletRequestAware {
	private HttpServletRequest request;
	private PatientFileService patientFileService;
	private PatientFile file;

	public ManageGastricStatesAction() {
	}

	public String show() {
		User patient = getRequestUser();
		file = patientFileService.loadPatientFile(patient.getId());

		return Action.SUCCESS;
	}

	protected User getRequestUser() {
		return HttpRequestUtils.getUser(request);
	}

	public PatientFile getPatientFile() {
		return file;
	}

	public void setPatientFileService(PatientFileService patientFileService) {
		this.patientFileService = patientFileService;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
