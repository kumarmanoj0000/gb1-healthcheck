package com.gb1.healthcheck.domain.meals;

import junit.framework.TestCase;

import com.gb1.healthcheck.domain.foods.FoodGroup;
import com.gb1.healthcheck.domain.foods.Foods;

public class PreparedFoodTest extends TestCase {
	public void testContainsIngredient() {
		assertTrue(Meals.spaghettiDish().containsIngredient(Foods.spaghetti()));
		assertTrue(Meals.spaghettiDish().containsIngredient(Foods.water()));
		assertTrue(Meals.spaghettiDish().containsIngredient(Foods.tomato()));
	}

	public void testContainsGroup() {
		assertTrue(Meals.spaghettiDish().containsGroup(FoodGroup.FRUITS));
		assertTrue(Meals.spaghettiDish().containsGroup(FoodGroup.OTHERS)); // for water
	}
}
