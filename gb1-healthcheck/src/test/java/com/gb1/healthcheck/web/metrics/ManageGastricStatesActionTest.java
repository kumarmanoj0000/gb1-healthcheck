package com.gb1.healthcheck.web.metrics;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;

public class ManageGastricStatesActionTest extends TestCase {
	public void testLoadPatientFile() {
		ManageGastricStatesAction action = new ManageGastricStatesAction();
		String result = action.show();
		assertEquals(Action.SUCCESS, result);
	}
}
