package com.gb1.healthcheck.services.meals;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.Users;

public class FullMealHydraterTest extends TestCase {
	public void testHydrate() {
		final AtomicBoolean dishesWereLoaded = new AtomicBoolean(false);

		Meal meal = new Meal(Users.lg(), new Date()) {
			@Override
			public Set<PreparedFood> getDishes() {
				dishesWereLoaded.set(true);
				return super.getDishes();
			}
		};

		FullMealHydrater h = new FullMealHydrater();
		assertSame(meal, h.hydrate(meal));
		assertTrue(dishesWereLoaded.get());
	}
}
