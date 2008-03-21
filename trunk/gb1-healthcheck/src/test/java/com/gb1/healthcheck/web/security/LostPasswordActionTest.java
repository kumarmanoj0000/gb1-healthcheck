package com.gb1.healthcheck.web.security;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.UnknownUserException;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class LostPasswordActionTest extends TestCase {
	public void testOnSubmitOk() throws Exception {
		final String email = "user@gb1.com";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.sendLostPassword(email);
		EasyMock.replay(userSvc);

		LostPasswordAction action = new LostPasswordAction();
		action.setEmail(email);
		action.setUserService(userSvc);

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}

	public void testOnSubmitUnknownUser() throws Exception {
		final String email = "user@gb1.com";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.sendLostPassword(email);
		EasyMock.expectLastCall().andThrow(new UnknownUserException());
		EasyMock.replay(userSvc);

		LostPasswordAction action = new LostPasswordAction();
		action.setEmail(email);
		action.setUserService(userSvc);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
	}
}
