package com.gb1.struts2.security;

import junit.framework.TestCase;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.TestingAuthenticationToken;

import com.gb1.healthcheck.domain.users.AcegiUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.mock.MockActionInvocation;

public class AcegiUserInterceptorTestCase extends TestCase {
	public void testIntercept() throws Exception {
		User user = Users.gb();

		TestAction action = new TestAction();
		MockActionInvocation ai = new MockActionInvocation();
		ai.setAction(action);

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new TestingAuthenticationToken(new AcegiUserDetailsAdapter(user),
				"password", new GrantedAuthority[] {});
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		assertNull(action.getUser());

		AcegiUserInterceptor interceptor = new AcegiUserInterceptor();
		interceptor.intercept(ai);

		assertNotNull(action.getUser());
		assertSame(user, action.getUser());
	}

	private class TestAction {
		private User user;

		public User getUser() {
			return user;
		}

		@AuthenticatedUser
		public void setUser(User user) {
			this.user = user;
		}

		public String execute() {
			return Action.SUCCESS;
		}
	}
}
