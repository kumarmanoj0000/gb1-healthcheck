package com.gb1.healthcheck.web.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.security.ui.rememberme.AbstractRememberMeServices;

import com.opensymphony.xwork2.Action;

@Namespace("/public/security")
@ParentPackage("default")
@Result(type = ServletRedirectResult.class, value = "/")
public class LogoutAction implements ServletRequestAware, ServletResponseAware {
	private HttpServletRequest request;
	private HttpServletResponse response;

	public LogoutAction() {
	}

	public String execute() {
		request.getSession().invalidate();
		Cookie terminate = new Cookie(
				AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
		terminate.setMaxAge(0);
		response.addCookie(terminate);

		return Action.SUCCESS;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
