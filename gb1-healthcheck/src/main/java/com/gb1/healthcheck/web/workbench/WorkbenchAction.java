package com.gb1.healthcheck.web.workbench;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("workbenchAction")
@Scope("prototype")
public class WorkbenchAction {
	public String execute() {
		return Action.SUCCESS;
	}
}
