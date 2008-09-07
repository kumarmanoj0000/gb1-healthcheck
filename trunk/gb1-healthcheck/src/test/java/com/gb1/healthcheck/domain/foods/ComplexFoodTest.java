package com.gb1.healthcheck.domain.foods;

import junit.framework.TestCase;

public class ComplexFoodTest extends TestCase {
	public void testGetNutrients() {
		assertTrue(Foods.redWine().containsIngredient(Foods.redGrape()));
		assertTrue(Foods.redWine().isSourceOfNutrient(Nutrient.ALCOHOL));

		assertTrue(Foods.spaghetti().containsIngredient(Foods.water()));
		assertTrue(Foods.spaghetti().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	public void testIsPartOfGroup() {
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}
}
