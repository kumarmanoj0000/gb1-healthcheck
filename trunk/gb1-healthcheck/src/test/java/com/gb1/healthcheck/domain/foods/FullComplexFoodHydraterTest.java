package com.gb1.healthcheck.domain.foods;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.TestCase;

public class FullComplexFoodHydraterTest extends TestCase {
	public void testHydrate() {
		final AtomicBoolean ingredientsWereLoaded = new AtomicBoolean(false);

		ComplexFood food = new ComplexFood() {
			@Override
			public Set<Food> getIngredients() {
				ingredientsWereLoaded.set(true);
				return super.getIngredients();
			}
		};

		FullComplexFoodHydrater h = new FullComplexFoodHydrater();
		h.hydrate(food);
		assertTrue(ingredientsWereLoaded.get());
	}
}
