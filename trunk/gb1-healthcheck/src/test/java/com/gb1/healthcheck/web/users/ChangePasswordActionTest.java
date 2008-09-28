package com.gb1.healthcheck.web.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.InvalidPasswordException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.opensymphony.xwork2.Action;

public class ChangePasswordActionTest {
	@Test
	public void testExecute() throws Exception {
		User user = Users.gb();
		String currentPwd = "1";
		String newPwd = "2";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.changeUserPassword(user.getId(), currentPwd, newPwd);
		EasyMock.expectLastCall();
		EasyMock.replay(userSvc);

		ChangePasswordAction action = new ChangePasswordAction();
		action.userService = userSvc;
		action.setRequester(user);
		action.setCurrentPassword(currentPwd);
		action.setNewPassword1(newPwd);
		action.setNewPassword2(newPwd);

		assertEquals(Action.SUCCESS, action.execute());

		EasyMock.verify(userSvc);
	}

	@Test
	public void testExecuteBadCurrentPassword() throws Exception {
		User user = Users.gb();
		String currentPwd = "1";
		String newPwd = "2";

		UserService userSvc = EasyMock.createMock(UserService.class);
		userSvc.changeUserPassword(user.getId(), currentPwd, newPwd);
		EasyMock.expectLastCall().andThrow(new InvalidPasswordException());
		EasyMock.replay(userSvc);

		ChangePasswordAction action = new ChangePasswordAction();
		action.userService = userSvc;
		action.setRequester(user);
		action.setCurrentPassword(currentPwd);
		action.setNewPassword1(newPwd);
		action.setNewPassword2(newPwd);

		assertEquals(Action.INPUT, action.execute());
		assertTrue(action.hasActionErrors());

		EasyMock.verify(userSvc);
	}
}
