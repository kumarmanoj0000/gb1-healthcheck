package com.gb1.healthcheck.web.admin;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.web.users.EditUserActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

@ParentPackage("default")
@Results( {
		@Result(name = "input", value = "/views/admin/editUser.jsp"),
		@Result(type = ServletActionRedirectResult.class, value = "workbench", params = {
				"namespace", "/workbench", "parse", "true", "actionMessageKey",
				"${actionMessageKey}" }) })
@Validation
public class EditUserAction extends EditUserActionSupport {
	private Long userId;

	public EditUserAction() {
	}

	@Override
	protected User getUserToEdit() {
		User user = getUserService().loadUser(userId);
		return user;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
