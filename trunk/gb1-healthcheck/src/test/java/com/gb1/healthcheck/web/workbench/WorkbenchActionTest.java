package com.gb1.healthcheck.web.workbench;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class WorkbenchActionTest {
	@Test
	public void testExecute() {
		WorkbenchAction action = new WorkbenchAction();
		assertEquals(Action.SUCCESS, action.execute());
	}
}
