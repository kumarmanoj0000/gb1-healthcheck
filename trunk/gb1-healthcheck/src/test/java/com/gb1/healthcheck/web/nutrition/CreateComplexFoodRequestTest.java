package com.gb1.healthcheck.web.nutrition;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.nutrition.Foods;

public class CreateComplexFoodRequestTest extends TestCase {
	public void testLoadIngredients() {
		String[] ingredientIds = new String[] { Foods.tomato().getId().toString(),
				Foods.beef().getId().toString(), Foods.beefStock().getId().toString(),
				Foods.pasta().getId().toString() };

		CreateComplexFoodRequest req = new CreateComplexFoodRequest();
		req.setSelectedIngredientIds(ingredientIds);
	}
}
