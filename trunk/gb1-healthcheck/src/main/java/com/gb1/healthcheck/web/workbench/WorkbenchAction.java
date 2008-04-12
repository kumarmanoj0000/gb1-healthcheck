package com.gb1.healthcheck.web.workbench;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Result(value = "/views/workbench/workbench.jsp")
public class WorkbenchAction extends ActionSupport {
	public WorkbenchAction() {
	}

	@Override
	public String execute() {
		return Action.SUCCESS;
	}

	public void setActionMessageKey(String key) {
		clearErrorsAndMessages();
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
	}
}
