package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.LoginAlreadyExistsException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserActivationRequest;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class RegisterUserActionTest {
	@Test
	public void testRegister() throws Exception {
		final User user = Users.gb();

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.registerUser(user))
				.andReturn(new UserActivationRequest(user, null));
		EasyMock.replay(userSvc);

		RegisterUserAction action = new RegisterUserAction();
		action.userService = userSvc;

		action.getModel().setLogin(user.getLogin());

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}

	@Test
	public void testRegisterWithError() throws Exception {
		final User user = Users.gb();

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.registerUser(user)).andThrow(new LoginAlreadyExistsException(""));
		EasyMock.replay(userSvc);

		RegisterUserAction action = new RegisterUserAction();
		action.userService = userSvc;

		action.getModel().setLogin(user.getLogin());

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasFieldErrors());
	}
}
