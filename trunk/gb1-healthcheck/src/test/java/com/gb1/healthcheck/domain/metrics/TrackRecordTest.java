package com.gb1.healthcheck.domain.metrics;

import java.util.Date;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.users.Users;

public class TrackRecordTest extends TestCase {
	public void testEquals() {
		TrackRecord tr1 = new TrackRecord(Users.gb());
		TrackRecord tr2 = new TrackRecord(Users.gb());
		TrackRecord tr3 = new TrackRecord(Users.lg());

		assertTrue(tr1.equals(tr2));
		assertFalse(tr1.equals(tr3));
	}

	public void testMeals() {
		Meal meal = Meals.fullItalianDinner();
		TrackRecord tr = new TrackRecord(Users.gb());

		tr.addMeal(meal);
		assertTrue(tr.getMeals().contains(meal));
		tr.removeMeal(meal);
		assertFalse(tr.getMeals().contains(meal));
	}

	public void testIntestinalStatuses() {
		Date now = new Date();

		TrackRecord tr = new TrackRecord(Users.gb());
		tr.addIntestinalStateMeasurement(IntestinalState.NORMAL, now);

		assertTrue(tr.getIntestinalStateMeasurements().contains(
				new IntestinalStateMeasurement(IntestinalState.NORMAL, now)));
	}
}
