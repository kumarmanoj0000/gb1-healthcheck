package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.users.Users;

public class PatientFileTest extends TestCase {
	public void testEquals() {
		PatientFile tr1 = new PatientFile(Users.gb());
		PatientFile tr2 = new PatientFile(Users.gb());
		PatientFile tr3 = new PatientFile(Users.lg());

		assertTrue(tr1.equals(tr2));
		assertFalse(tr1.equals(tr3));
	}

	public void testMeals() {
		Meal meal = Meals.fullItalianDinner();
		PatientFile tr = new PatientFile(Users.gb());

		tr.addMeal(meal);
		assertTrue(tr.getMeals().contains(meal));
		tr.removeMeal(meal);
		assertFalse(tr.getMeals().contains(meal));
	}

	public void testIntestinalStatuses() {
		Date now = new Date();

		PatientFile tr = new PatientFile(Users.gb());
		tr.addIntestinalStateMeasurement(IntestinalState.NORMAL, now);

		assertTrue(tr.getIntestinalStateMeasurements().contains(
				new IntestinalStateMeasurement(IntestinalState.NORMAL, now)));
	}
}
