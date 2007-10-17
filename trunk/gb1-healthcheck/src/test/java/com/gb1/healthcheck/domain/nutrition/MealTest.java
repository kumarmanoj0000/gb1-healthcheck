package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.Set;

import junit.framework.TestCase;

public class MealTest extends TestCase {
	public void testNewFromPropertyProvider() {
		final Date today = new Date();
		MealPropertyProvider pp = new MealPropertyProvider() {
			public Date getDateAndTime() {
				return today;
			}

			public Set<PreparedFood> getDishes() {
				return Meals.allDishes();
			}
		};

		Meal dinner = new Meal(pp);
		assertEquals(today, dinner.getDateAndTime());
		assertTrue(dinner.containsFood(Foods.spaghetti()));
		assertTrue(dinner.containsFood(Foods.redWine()));
	}

	public void testContainsFood() {
		Meal dinner = new Meal(new Date());
		dinner.addDish(Meals.spaghettiDish());
		dinner.addDish(Meals.redWineDrink());

		assertTrue(dinner.containsFood(Foods.tomato()));
		assertTrue(dinner.containsFood(Foods.redGrape()));
		assertTrue(dinner.containsGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		Meal dinner = new Meal(new Date());
		dinner.addDish(Meals.spaghettiDish());
		dinner.addDish(Meals.redWineDrink());

		assertTrue(dinner.isSourceOfNutrient(Nutrient.VITAMIN_C));
		assertTrue(dinner.isSourceOfNutrient(Nutrient.ALCOHOL));
	}
}
