package com.gb1.healthcheck.web.nutrition;

import java.util.Arrays;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.Food;
import com.gb1.healthcheck.domain.nutrition.Foods;

public class ComplexFoodUpdateRequestTest extends TestCase {
	public void testNewRequestFromFood() {
		final ComplexFood food = Foods.spaghetti();
		ComplexFoodUpdateRequest req = new ComplexFoodUpdateRequest(food);

		Long[] selectedIngredientIds = new Long[food.getIngredients().size()];
		int i = 0;
		for (Food f : food.getIngredients()) {
			selectedIngredientIds[i++] = f.getId();
		}

		assertEquals(food.getName(), req.getName());
		assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(selectedIngredientIds), Arrays
				.asList(req.getSelectedIngredientIds())));
	}
}
