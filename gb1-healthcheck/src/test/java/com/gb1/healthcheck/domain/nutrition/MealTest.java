package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;

import junit.framework.TestCase;

public class MealTest extends TestCase {
	public void testMealOk() {
		Meal dinner = new Meal(new Date());
		dinner.addDish(Meals.spaghettiDish());
		dinner.addDish(Meals.redWineDrink());

		assertTrue(dinner.containsFood(Foods.tomato()));
		assertTrue(dinner.containsFood(Foods.redGrape()));
		assertTrue(dinner.containsGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
		assertTrue(dinner.isSourceOfNutrient(Nutrient.VITAMIN_C));
		assertTrue(dinner.isSourceOfNutrient(Nutrient.ALCOHOL));
	}
}
