package com.gb1.healthcheck.web.metrics;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.gb1.healthcheck.domain.metrics.GastricState;
import com.gb1.healthcheck.domain.metrics.PatientFile;
import com.gb1.healthcheck.domain.metrics.PunctualGastricState;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.services.metrics.PatientFileService;
import com.gb1.struts2.interceptor.AuthenticatedUser;
import com.opensymphony.xwork2.Action;

@ParentPackage("default")
@Result(value = "/views/metrics/gastricStates.jsp")
public class ManageGastricStatesAction {
	@Resource
	protected PatientFileService patientFileService;

	private User requester;

	public ManageGastricStatesAction() {
	}

	public String execute() {
		return Action.SUCCESS;
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
		return requester;
	}

	@AuthenticatedUser
	public void setRequester(User requester) {
		this.requester = requester;
	}
}
