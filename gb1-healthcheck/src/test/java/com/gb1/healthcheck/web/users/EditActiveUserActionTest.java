package com.gb1.healthcheck.web.users;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.EmailAlreadyExistsException;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.users.UserService;
import com.gb1.healthcheck.services.users.UserUpdateRequest;
import com.opensymphony.xwork2.Action;

public class EditActiveUserActionTest extends TestCase {
	public void testInput() throws Exception {
		final User user = Users.gb();
		EditActiveUserAction action = createAction(user);
		action.setSession(new HashMap<String, Object>());

		assertEquals(Action.INPUT, action.input());
	}

	public void testSubmit() throws Exception {
		User user = Users.gb();
		BasicUserUpdateRequest request = new BasicUserUpdateRequest(user);
		request.setEmail("newEmail@yahoo.com");

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(EditActiveUserAction.MODEL_SESSION_KEY, request);

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.updateUser(request)).andReturn(user);
		EasyMock.replay(userSvc);

		EditActiveUserAction action = createAction(user);
		action.setUserService(userSvc);
		action.setSession(sessionMap);

		assertEquals(Action.SUCCESS, action.execute());
		assertTrue(sessionMap.isEmpty());

		// make sure the update was sent to the service layer
		EasyMock.verify(userSvc);

		// make sure the session's Acegi authenticated user was updated too
		assertEquals(request.getEmail(), user.getEmail());
	}

	public void testSubmitWithErrors() throws Exception {
		User user = Users.gb();
		UserUpdateRequest request = new BasicUserUpdateRequest(user);

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(EditActiveUserAction.MODEL_SESSION_KEY, request);

		UserService userSvc = EasyMock.createMock(UserService.class);
		EasyMock.expect(userSvc.updateUser(request)).andThrow(
				new EmailAlreadyExistsException(user.getEmail()));
		EasyMock.replay(userSvc);

		EditActiveUserAction action = createAction(user);
		action.setUserService(userSvc);
		action.setSession(sessionMap);

		assertEquals(Action.INPUT, action.execute());
		assertNotNull(action.getFieldErrors().get("model.email"));
		assertTrue(sessionMap.containsKey(EditActiveUserAction.MODEL_SESSION_KEY));
	}

	public void testCancel() {
		User user = Users.gb();
		EditActiveUserAction action = createAction(user);
		assertEquals(Action.SUCCESS, action.cancel());
	}

	private EditActiveUserAction createAction(final User user) {
		EditActiveUserAction action = new EditActiveUserAction() {
			@Override
			public User getUser() {
				return user;
			}
		};
		return action;
	}
}