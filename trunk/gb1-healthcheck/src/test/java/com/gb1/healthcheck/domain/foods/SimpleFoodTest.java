package com.gb1.healthcheck.domain.foods;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimpleFoodTest {
	@Test
	public void testIsPartOfGroup() {
		assertTrue(Foods.apple().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertFalse(Foods.apple().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	@Test
	public void testIsSourceOfNutrient() {
		assertTrue(Foods.apple().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}
}
