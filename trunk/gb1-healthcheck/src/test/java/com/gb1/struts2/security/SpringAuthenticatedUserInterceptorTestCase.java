package com.gb1.struts2.security;

import junit.framework.TestCase;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;

import com.gb1.healthcheck.domain.users.SpringUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.mock.MockActionInvocation;

public class SpringAuthenticatedUserInterceptorTestCase extends TestCase {
	public void testIntercept() throws Exception {
		User user = Users.gb();

		TestAction action = new TestAction();
		MockActionInvocation ai = new MockActionInvocation();
		ai.setAction(action);

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new TestingAuthenticationToken(new SpringUserDetailsAdapter(user),
				"password", new GrantedAuthority[] {});
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		assertNull(action.getUser());

		SpringAuthenticatedUserInterceptor interceptor = new SpringAuthenticatedUserInterceptor();
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
