package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.TestCase;

public class FullMealHydraterTest extends TestCase {
	public void testHydrate() {
		final AtomicBoolean dishesWereLoaded = new AtomicBoolean(false);

		Meal meal = new Meal() {
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
