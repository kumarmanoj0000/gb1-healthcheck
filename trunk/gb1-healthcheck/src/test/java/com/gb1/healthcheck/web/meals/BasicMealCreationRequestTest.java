package com.gb1.healthcheck.web.meals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.meals.Meals;
import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.domain.meals.PreparedFood;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;

public class BasicMealCreationRequestTest extends TestCase {
	public void testCreate() {
		final User eater = Users.lg();

		BasicMealCreationRequest req = new BasicMealCreationRequest(eater);
		assertNull(req.getMealId());
		assertEquals(eater.getId(), req.getEaterId());
	}

	public void testGetCreationRequests() {
		Map<Long, Food> foods = new HashMap<Long, Food>();
		foods.put(Foods.spaghetti().getId(), Foods.spaghetti());
		foods.put(Foods.redWine().getId(), Foods.redWine());

		Map<String, PreparationMethod> prepMethods = new HashMap<String, PreparationMethod>();
		prepMethods.put(PreparationMethod.STEWED.name(), PreparationMethod.STEWED);
		prepMethods.put(PreparationMethod.RAW.name(), PreparationMethod.RAW);

		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		dishes.add(Meals.spaghettiDish());
		dishes.add(Meals.redWineDrink());

		BasicMealCreationRequest req = new BasicMealCreationRequest(Users.lg());
		req.setDishes(dishes);

		assertTrue(CollectionUtils.isEqualCollection(foods.keySet(), Arrays.asList(req
				.getSelectedFoodIds())));
		assertTrue(CollectionUtils.isEqualCollection(prepMethods.keySet(), Arrays.asList(req
				.getSelectedPreparationMethodNames())));

		for (PreparedFoodCreationRequest dcr : req.getDishCreationRequests()) {
			assertTrue(foods.containsKey(dcr.getIngredientId()));
			assertTrue(prepMethods.containsValue(dcr.getPreparationMethod()));
		}
	}
}
