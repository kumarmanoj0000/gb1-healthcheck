package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.core.Token;
import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.InvalidTokenException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ActivateUserActionTest {
	@Test
	public void testActivateOk() throws Exception {
		final String email = "user@gb.com";
		final String token = "123";

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.activateUser(email, new Token(token))).andReturn(new ExposedUser());
		EasyMock.replay(userSvc);

		ActivateUserAction action = new ActivateUserAction();
		action.setPrincipal(email);
		action.setCredentials(token);
		action.userService = userSvc;

		assertEquals(Action.SUCCESS, action.execute());
	}

	@Test
	public void testActivateError() throws Exception {
		final String email = "user@gb.com";
		final String token = "123";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.activateUser(email, new Token(token));
		EasyMock.expectLastCall().andThrow(new InvalidTokenException());
		EasyMock.replay(userSvc);

		ActivateUserAction action = new ActivateUserAction();
		action.setPrincipal(email);
		action.setCredentials(token);
		action.userService = userSvc;

		assertEquals(Action.INPUT, action.execute());
	}
}
