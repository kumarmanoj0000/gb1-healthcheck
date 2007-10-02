package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.FoodRepository;
import com.gb1.healthcheck.domain.nutrition.Foods;

public class ComplexFoodCreationRequestTest extends TestCase {
	public void testLoadIngredients() {
		Map<String, Food> ingredients = new HashMap<String, Food>();
		ingredients.put(Foods.tomato().getId().toString(), Foods.tomato());
		ingredients.put(Foods.beef().getId().toString(), Foods.beef());
		ingredients.put(Foods.beefStock().getId().toString(), Foods.beefStock());
		ingredients.put(Foods.pasta().getId().toString(), Foods.pasta());

		final String[] ingredientIds = ingredients.keySet().toArray(new String[0]);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (String foodId : ingredients.keySet()) {
			EasyMock.expect(foodRepo.loadFood(Long.parseLong(foodId))).andReturn(
					ingredients.get(foodId));
		}
		EasyMock.replay(foodRepo);

		ComplexFoodCreationRequest req = new ComplexFoodCreationRequest();
		req.setFoodRepository(foodRepo);
		req.setSelectedIngredientIds(ingredientIds);

		assertTrue(CollectionUtils.isEqualCollection(ingredients.values(), req.getIngredients()));
		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(ingredientIds), Arrays
				.asList(req.getSelectedIngredientIds())));
	}
}
