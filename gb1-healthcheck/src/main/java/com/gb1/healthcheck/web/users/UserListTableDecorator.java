package com.gb1.healthcheck.web.users;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.gb1.commons.Identifiable;
import com.gb1.healthcheck.web.utils.HttpRequestUtils;

public class UserListTableDecorator extends TableDecorator {
	private static final String RESET_PASSWORD_BASE_URL = "resetPassword.go?userId=";
	private String resetPasswordLabel;

	public UserListTableDecorator() {
	}

	@Override
	public void init(PageContext pageContext, Object decorated, TableModel tableModel) {
		resetPasswordLabel = HttpRequestUtils.resolveResourceKey("users.resetPassword", "Reset",
				pageContext);
	}

	public String getResetPasswordLink() {
		Long id = ((Identifiable) getCurrentRowObject()).getId();
		String link = "<a href='" + RESET_PASSWORD_BASE_URL + id + "'>" + resetPasswordLabel
				+ "</a>";

		return link;
	}
}
