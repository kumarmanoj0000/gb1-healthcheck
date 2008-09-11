package com.gb1.healthcheck.web.meals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

public class BasicMealCreationRequestTest {
	@Test
	public void testCreate() {
		final User eater = Users.lg();

		BasicMealCreationRequest req = new BasicMealCreationRequest(eater);
		assertNull(req.getMealId());
		assertEquals(eater.getId(), req.getEaterId());
	}

	@Test
	public void testGetCreationRequests() {
		Map<Long, Food> foods = new HashMap<Long, Food>();
		foods.put(Foods.spaghetti().getId(), Foods.spaghetti());
		foods.put(Foods.redWine().getId(), Foods.redWine());

		Map<String, PreparationMethod> prepMethods = new HashMap<String, PreparationMethod>();
		prepMethods.put(PreparationMethod.STEWED.name(), PreparationMethod.STEWED);
		prepMethods.put(PreparationMethod.RAW.name(), PreparationMethod.RAW);

		BasicMealCreationRequest req = new BasicMealCreationRequest(Users.lg());
		req.addDish(Meals.spaghettiDish());
		req.addDish(Meals.redWineDrink());

		for (PreparedFoodCreationRequest dcr : req.getDishCreationRequests()) {
			assertTrue(foods.containsKey(dcr.getIngredientId()));
			assertTrue(prepMethods.containsValue(dcr.getPreparationMethod()));
		}
	}
}
