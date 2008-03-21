package com.gb1.healthcheck.web.users;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.LoginAlreadyExistsException;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class UserRegistrationActionTest extends TestCase {
	public void testRegister() throws Exception {
		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.registerUser(EasyMock.isA(BasicUserRegistrationRequest.class)))
				.andReturn(new UserActivationRequest(null, null));
		EasyMock.replay(userSvc);

		UserRegistrationAction action = new UserRegistrationAction();
		action.setUserService(userSvc);
		String result = action.execute();

		assertEquals(Action.SUCCESS, result);
		EasyMock.verify(userSvc);
	}

	public void testRegisterWithError() throws Exception {
		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.registerUser(EasyMock.isA(BasicUserRegistrationRequest.class)))
				.andThrow(new LoginAlreadyExistsException(""));
		EasyMock.replay(userSvc);

		UserRegistrationAction action = new UserRegistrationAction();
		action.setUserService(userSvc);
		String result = action.execute();

		assertEquals(Action.INPUT, result);
		assertTrue(action.hasFieldErrors());
	}
}
