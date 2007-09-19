package com.gb1.healthcheck.domain.medication;

import java.util.Date;

import junit.framework.TestCase;

public class DrugIntakeTest extends TestCase {
	public void testNewDrugIntake() {
		DrugIntake intake = new DrugIntake(Drugs.librax(), new Date());
		assertEquals(1, intake.getQuantity());
	}

	public void testNewDrugInvalidQuantity() {
		try {
			new DrugIntake(Drugs.librax(), -1, new Date());
			fail("Cannot create intake with negative quantity");
		}
		catch (IllegalArgumentException e) {
			// ok
		}
	}
}
