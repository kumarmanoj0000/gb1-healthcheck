package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ResetPasswordActionTest {
	@Test
	public void testExecute() {
		final User user = Users.gb();

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.resetUserPassword(user.getId());
		EasyMock.expectLastCall();
		EasyMock.replay(userSvc);

		ResetPasswordAction action = new ResetPasswordAction();
		action.userService = userSvc;
		action.setUserId(user.getId());

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}
}
