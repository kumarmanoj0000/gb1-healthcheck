package com.gb1.healthcheck.domain.nutrition;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;

public class JpaMealRepositoryTest extends BaseRepositoryTestCase {
	private MealRepository mealRepo = null;

	public void testLoadMeals() {
		Meal meal1 = new Meal(parseDateAndTime("2007-10-15 16:00")).addDish(Meals.redWineDrink());
		Meal meal2 = new Meal(parseDateAndTime("2007-10-15 18:30")).addDish(Meals.redWineDrink())
				.addDish(Meals.spaghettiDish());
		Meal meal3 = new Meal(parseDateAndTime("2007-10-16 18:00")).addDish(Meals.spaghettiDish());

		List<Meal> meals = mealRepo.loadMeals();

		assertTrue(meals.contains(meal1));
		assertTrue(meals.contains(meal2));
		assertTrue(meals.contains(meal3));
	}

	public void testFindMealsByDateAndTime() {
		Date mealDateAndTime = parseDateAndTime("2007-10-16 18:00");
		Meal meal = new Meal(mealDateAndTime).addDish(Meals.spaghettiDish());

		List<Meal> meals = mealRepo.findMealsByDateAndTime(mealDateAndTime);

		assertEquals(1, meals.size());
		assertTrue(meals.contains(meal));
	}

	private Date parseDateAndTime(String text) {
		Date dateAndTime = null;

		try {
			dateAndTime = DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
		}
		catch (ParseException e) {
		}

		return dateAndTime;
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}
}
