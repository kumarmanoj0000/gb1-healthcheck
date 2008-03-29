package com.gb1.healthcheck.web.users;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;

public class ListUsersActionTest extends TestCase {
	public void testExecute() {
		ListUsersAction action = new ListUsersAction();
		assertEquals(Action.SUCCESS, action.execute());
	}
}
