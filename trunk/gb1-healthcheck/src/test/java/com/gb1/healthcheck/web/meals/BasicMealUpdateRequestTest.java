package com.gb1.healthcheck.web.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.PreparedFoodUpdateRequest;

public class BasicMealUpdateRequestTest {
	@Test
	public void testNewRequestFromMeal() {
		final Meal meal = new Meal(Users.gb(), new Date()).addDish(Meals.spaghettiDish()).addDish(
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

		for (PreparedFoodUpdateRequest dur : req.getDishUpdateRequests()) {
			assertTrue(ArrayUtils.contains(selectedFoodIds, dur.getIngredientId()));
			assertTrue(ArrayUtils.contains(selectedPrepMethodNames, dur.getPreparationMethod()
					.name()));
		}
	}
}
