package com.gb1.healthcheck.domain.meals;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Foods;
import com.gb1.healthcheck.domain.foods.Nutrient;

public class MealTest {
	@Test
	public void testContainsFood() {
		Meal meal = Meals.fullItalianDinner();

		assertTrue(meal.containsFood(Foods.tomato()));
		assertTrue(meal.containsFood(Foods.redGrape()));
		assertTrue(meal.containsGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	@Test
	public void testIsSourceOfNutrient() {
		Meal meal = Meals.fullItalianDinner();

		assertTrue(meal.isSourceOfNutrient(Nutrient.VITAMIN_C));
		assertTrue(meal.isSourceOfNutrient(Nutrient.ALCOHOL));
	}
}
