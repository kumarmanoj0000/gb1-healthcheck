package com.gb1.healthcheck.web.workbench;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("workbenchAction")
@Scope("prototype")
public class WorkbenchAction extends ActionSupport {
	public WorkbenchAction() {
	}

	@Override
	public String execute() {
		return Action.SUCCESS;
	}

	public void setActionMessageKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			addActionMessage(getText(key));
		}
	}
}
