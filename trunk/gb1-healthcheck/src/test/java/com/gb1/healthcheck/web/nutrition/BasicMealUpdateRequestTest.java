package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;

public class BasicMealUpdateRequestTest extends TestCase {
	public void testNewRequestFromMeal() {
		final Date now = new Date();
		final Meal meal = new Meal(now).addDish(Meals.spaghettiDish())
				.addDish(Meals.redWineDrink());
		BasicMealUpdateRequest req = new BasicMealUpdateRequest(meal);

		int i = 0;
		Long[] selectedFoodIds = new Long[meal.getDishes().size()];
		String[] selectedPrepMethodNames = new String[meal.getDishes().size()];

		for (PreparedFood dish : meal.getDishes()) {
			selectedFoodIds[i] = dish.getIngredient().getId();
			selectedPrepMethodNames[i] = dish.getPreparationMethod().name();
			i++;
		}

		assertEquals(meal.getInstant(), req.getInstant());
		assertTrue(Arrays.equals(selectedFoodIds, req.getSelectedFoodIds()));
		assertTrue(Arrays.equals(selectedPrepMethodNames, req.getSelectedPreparationMethodNames()));
	}
}
