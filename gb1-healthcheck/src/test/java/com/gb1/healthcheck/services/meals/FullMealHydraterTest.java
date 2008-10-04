package com.gb1.healthcheck.services.meals;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.Users;

public class FullMealHydraterTest {
	@Test
	public void testHydrate() {
		final AtomicBoolean dishesWereLoaded = new AtomicBoolean(false);

		Meal meal = new Meal() {
			@Override
			public List<PreparedFood> getDishes() {
				dishesWereLoaded.set(true);
				return super.getDishes();
			}
		};
		meal.setEater(Users.lg());

		FullMealHydrater h = new FullMealHydrater();
		assertSame(meal, h.hydrate(meal));
		assertTrue(dishesWereLoaded.get());
	}
}
