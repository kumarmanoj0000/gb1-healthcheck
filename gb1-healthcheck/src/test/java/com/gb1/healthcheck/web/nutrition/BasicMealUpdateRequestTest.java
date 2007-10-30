package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;
import com.gb1.healthcheck.domain.nutrition.PreparedFoodUpdateRequest;

public class BasicMealUpdateRequestTest extends TestCase {
	public void testNewRequestFromMeal() {
		final Meal meal = new Meal(new Date()).addDish(Meals.spaghettiDish()).addDish(
				Meals.redWineDrink());
		BasicMealUpdateRequest req = new BasicMealUpdateRequest(meal);

		Long[] selectedFoodIds = new Long[meal.getDishes().size()];
		String[] selectedPrepMethodNames = new String[meal.getDishes().size()];

		int i = 0;
		for (PreparedFood dish : meal.getDishes()) {
			selectedFoodIds[i] = dish.getIngredient().getId();
			selectedPrepMethodNames[i] = dish.getPreparationMethod().name();
			i++;
		}

		assertEquals(meal.getInstant(), req.getInstant());

		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(selectedFoodIds), Arrays
				.asList(req.getSelectedFoodIds())));
		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(selectedPrepMethodNames), Arrays
				.asList(req.getSelectedPreparationMethodNames())));

		for (PreparedFoodUpdateRequest dur : req.getDishUpdateRequests()) {
			assertTrue(ArrayUtils.contains(selectedFoodIds, dur.getIngredientId()));
			assertTrue(ArrayUtils.contains(selectedPrepMethodNames, dur.getPreparationMethod()
					.name()));
		}
	}
}
