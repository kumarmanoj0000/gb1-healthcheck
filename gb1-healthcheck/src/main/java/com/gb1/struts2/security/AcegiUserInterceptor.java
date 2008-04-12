package com.gb1.struts2.security;

import java.lang.reflect.Method;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

import com.gb1.healthcheck.domain.users.AcegiUserDetailsAdapter;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Interceptor that injects the authenticated user to all action methods that are marked with the
 * AuthenticatedUser annotation. Inspired by Practical Apache Struts2 Web 2.0 Projects by Ian
 * Roughley.
 * 
 * @author Guillaume Bilodeau
 */
public class AcegiUserInterceptor extends AbstractInterceptor {
	public AcegiUserInterceptor() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser != null) {
			for (Method m : action.getClass().getDeclaredMethods()) {
				if (m.getAnnotation(AuthenticatedUser.class) != null
						&& currentUser.getPrincipal() instanceof AcegiUserDetailsAdapter) {
					AcegiUserDetailsAdapter adapter = (AcegiUserDetailsAdapter) currentUser
							.getPrincipal();
					m.invoke(action, adapter.getUser());
				}
			}
		}

		return invocation.invoke();
	}
}
