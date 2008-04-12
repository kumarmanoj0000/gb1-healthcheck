package com.gb1.healthcheck.web.admin;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ResetPasswordActionTest extends TestCase {
	public void testExecute() {
		final User user = Users.gb();

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.resetUserPassword(user.getId());
		EasyMock.expectLastCall();
		EasyMock.replay(userSvc);

		ResetPasswordAction action = new ResetPasswordAction();
		action.setUserService(userSvc);
		action.setUserId(user.getId());

		assertEquals(Action.SUCCESS, action.execute());
		EasyMock.verify(userSvc);
	}
}
