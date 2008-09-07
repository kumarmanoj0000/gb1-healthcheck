package com.gb1.healthcheck.domain.foods;

import junit.framework.TestCase;

public class SimpleFoodTest extends TestCase {
	public void testIsPartOfGroup() {
		assertTrue(Foods.apple().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertFalse(Foods.apple().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		assertTrue(Foods.apple().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}
}
