package com.gb1.healthcheck.web.workbench;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;

public class WorkbenchActionTest extends TestCase {
	public void testExecute() {
		WorkbenchAction action = new WorkbenchAction();
		assertEquals(Action.SUCCESS, action.execute());
	}
}
