package com.gb1.healthcheck.domain.foods;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComplexFoodTest {
	@Test
	public void testGetNutrients() {
		assertTrue(Foods.redWine().containsIngredient(Foods.redGrape()));
		assertTrue(Foods.redWine().isSourceOfNutrient(Nutrient.ALCOHOL));

		assertTrue(Foods.spaghetti().containsIngredient(Foods.water()));
		assertTrue(Foods.spaghetti().isSourceOfNutrient(Nutrient.VITAMIN_C));
	}

	@Test
	public void testIsPartOfGroup() {
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.FRUITS));
		assertTrue(Foods.spaghetti().isPartOfFoodGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}
}
