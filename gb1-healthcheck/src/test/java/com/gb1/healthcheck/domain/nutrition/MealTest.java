package com.gb1.healthcheck.domain.nutrition;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.lang.time.DateUtils;

public class MealTest extends TestCase {
	public void testNewFromPropertyProvider() {
		final Date today = new Date();
		MealCreationPropertyProvider pp = new MealCreationPropertyProvider() {
			public Date getInstant() {
				return today;
			}

			public Set<PreparedFood> getDishes() {
				return Meals.allDishes();
			}
		};

		Meal dinner = new Meal(pp);
		assertEquals(today, dinner.getInstant());
		assertTrue(dinner.containsFood(Foods.spaghetti()));
		assertTrue(dinner.containsFood(Foods.redWine()));
	}

	public void testContainsFood() {
		Meal meal = Meals.fullItalianDinner();

		assertTrue(meal.containsFood(Foods.tomato()));
		assertTrue(meal.containsFood(Foods.redGrape()));
		assertTrue(meal.containsGroup(FoodGroup.MEAT_AND_SUBSTITUTES));
	}

	public void testIsSourceOfNutrient() {
		Meal meal = Meals.fullItalianDinner();

		assertTrue(meal.isSourceOfNutrient(Nutrient.VITAMIN_C));
		assertTrue(meal.isSourceOfNutrient(Nutrient.ALCOHOL));
	}

	public void testUpdateFromPropertyProvider() throws ParseException {
		final Date oldInstant = parseInstant("2007-10-15 18:00");
		final Date newInstant = parseInstant("2007-10-15 18:30");

		MealUpdatePropertyProvider pp = new MealUpdatePropertyProvider() {
			public Set<PreparedFood> getDishes() {
				Set<PreparedFood> dishes = new HashSet<PreparedFood>();
				dishes.add(Meals.spaghettiDish());
				dishes.add(Meals.waterDrink());

				return dishes;
			}

			public Date getInstant() {
				return newInstant;
			}
		};

		Meal dinner = new Meal(oldInstant);
		dinner.addDish(Meals.spaghettiDish());
		dinner.addDish(Meals.redWineDrink());

		dinner.update(pp);

		assertEquals(newInstant, dinner.getInstant());
		assertEquals(2, dinner.getDishes().size());
		assertTrue(dinner.getDishes().contains(Meals.spaghettiDish()));
		assertTrue(dinner.getDishes().contains(Meals.waterDrink()));
		assertFalse(dinner.getDishes().contains(Meals.redWineDrink()));
	}

	private Date parseInstant(String text) throws ParseException {
		return DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
	}
}
