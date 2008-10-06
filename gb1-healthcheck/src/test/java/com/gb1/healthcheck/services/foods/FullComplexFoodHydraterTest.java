package com.gb1.healthcheck.services.foods;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;

public class FullComplexFoodHydraterTest {
	@Test
	public void testHydrate() {
		final AtomicBoolean ingredientsWereLoaded = new AtomicBoolean(false);

		ComplexFood food = new ComplexFood() {
			@Override
			public List<Food> getIngredients() {
				ingredientsWereLoaded.set(true);
				return super.getIngredients();
			}
		};

		FullComplexFoodHydrater h = new FullComplexFoodHydrater();
		h.hydrate(food);
		assertTrue(ingredientsWereLoaded.get());
	}
}
