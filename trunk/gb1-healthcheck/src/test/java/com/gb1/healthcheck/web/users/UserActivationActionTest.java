package com.gb1.healthcheck.web.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.commons.tokens.Token;
import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.InvalidTokenException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class UserActivationActionTest extends TestCase {
	public void testActivateOk() throws Exception {
		final String email = "user@gb.com";
		final String token = "123";

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.activateUser(email, new Token(token))).andReturn(new ExposedUser());
		EasyMock.replay(userSvc);

		UserActivationAction action = new UserActivationAction();
		action.setPrincipal(email);
		action.setCredentials(token);
		action.setUserService(userSvc);

		assertEquals(Action.SUCCESS, action.activate());
	}

	public void testActivateError() throws Exception {
		final String email = "user@gb.com";
		final String token = "123";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.activateUser(email, new Token(token));
		EasyMock.expectLastCall().andThrow(new InvalidTokenException());
		EasyMock.replay(userSvc);

		UserActivationAction action = new UserActivationAction();
		action.setPrincipal(email);
		action.setCredentials(token);
		action.setUserService(userSvc);

		assertEquals(Action.INPUT, action.activate());
	}
}
