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
		Map<Long, Food> ingredients = new HashMap<Long, Food>();
		ingredients.put(Foods.tomato().getId(), Foods.tomato());
		ingredients.put(Foods.beef().getId(), Foods.beef());
		ingredients.put(Foods.beefStock().getId(), Foods.beefStock());
		ingredients.put(Foods.pasta().getId(), Foods.pasta());

		final Long[] ingredientIds = ingredients.keySet().toArray(new Long[0]);

		FoodRepository foodRepo = EasyMock.createMock(FoodRepository.class);
		for (Long foodId : ingredients.keySet()) {
			EasyMock.expect(foodRepo.loadFood(foodId)).andReturn(ingredients.get(foodId));
		}
		EasyMock.replay(foodRepo);

		BasicComplexFoodCreationRequest req = new BasicComplexFoodCreationRequest();
		req.setFoodRepository(foodRepo);
		req.setSelectedIngredientIds(ingredientIds);

		assertTrue(CollectionUtils.isEqualCollection(ingredients.values(), req.getIngredients()));
		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(ingredientIds), Arrays
				.asList(req.getSelectedIngredientIds())));
	}
}
