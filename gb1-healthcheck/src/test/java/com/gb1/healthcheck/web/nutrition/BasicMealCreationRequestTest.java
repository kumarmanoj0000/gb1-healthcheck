package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;
import com.gb1.healthcheck.domain.nutrition.Meals;
import com.gb1.healthcheck.domain.nutrition.PreparationMethod;
import com.gb1.healthcheck.domain.nutrition.PreparedFood;

public class BasicMealCreationRequestTest extends TestCase {
	public void testGetDishes() {
		Map<Long, Food> foods = new HashMap<Long, Food>();
		foods.put(Foods.spaghetti().getId(), Foods.spaghetti());
		foods.put(Foods.redWine().getId(), Foods.redWine());

		Map<String, PreparationMethod> prepMethods = new HashMap<String, PreparationMethod>();
		prepMethods.put(PreparationMethod.STEWED.name(), PreparationMethod.STEWED);
		prepMethods.put(PreparationMethod.RAW.name(), PreparationMethod.RAW);

		Long[] foodIds = foods.keySet().toArray(new Long[0]);
		String[] prepMethodNames = prepMethods.keySet().toArray(new String[0]);

		Set<PreparedFood> dishes = new HashSet<PreparedFood>();
		dishes.add(Meals.spaghettiDish());
		dishes.add(Meals.redWineDrink());

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (Long foodId : foods.keySet()) {
			EasyMock.expect(foodRepo.loadFood(foodId)).andReturn(foods.get(foodId));
		}
		EasyMock.replay(foodRepo);

		MealRequestSupport req = new MealCreationRequest();
		req.setFoodRepository(foodRepo);
		req.setSelectedFoodIds(foodIds);
		req.setSelectedPreparationMethodNames(prepMethodNames);

		assertTrue(Arrays.equals(foodIds, req.getSelectedFoodIds()));
		assertTrue(Arrays.equals(prepMethodNames, req.getSelectedPreparationMethodNames()));

		assertTrue(CollectionUtils.isEqualCollection(dishes, req.getDishes()));
	}
}
