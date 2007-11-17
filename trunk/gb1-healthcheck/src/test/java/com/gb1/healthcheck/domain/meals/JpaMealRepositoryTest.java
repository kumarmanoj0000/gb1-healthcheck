package com.gb1.healthcheck.domain.meals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.gb1.healthcheck.domain.support.BaseRepositoryTestCase;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;

public class JpaMealRepositoryTest extends BaseRepositoryTestCase {
	private MealRepository mealRepo = null;

	public void testLoadMeals() throws ParseException {
		final User eater = Users.gb();

		Meal meal1 = new Meal(eater, parseInstant("2007-10-15 16:00"))
				.addDish(Meals.redWineDrink());
		Meal meal2 = new Meal(eater, parseInstant("2007-10-15 18:30"))
				.addDish(Meals.redWineDrink()).addDish(Meals.spaghettiDish());

		List<Meal> meals = mealRepo.findMealsBy(eater);
		assertEquals(2, meals.size());
		assertTrue(meals.contains(meal1));
		assertTrue(meals.contains(meal2));
	}

	public void testFindMealsByInstant() throws ParseException {
		final User eater = Users.lg();
		Date mealInstant = parseInstant("2007-10-16 18:00");
		Meal meal = new Meal(eater, mealInstant).addDish(Meals.spaghettiDish());

		List<Meal> meals = mealRepo.findMealsBy(eater, mealInstant);

		assertEquals(1, meals.size());
		assertTrue(meals.contains(meal));
	}

	public void testSaveMeal() throws ParseException {
		Date mealInstant = parseInstant("2007-10-16 18:00");
		Meal meal = new Meal(Users.lg(), mealInstant).addDish(Meals.spaghettiDish());
		mealRepo.saveMeal(meal);

		assertEquals(meal, mealRepo.loadMeal(meal.getId()));
	}

	public void testDeleteMeal() {
		final Long mealId = 1L;
		mealRepo.deleteMeal(mealId);
		assertNull(mealRepo.loadMeal(mealId));
	}

	private Date parseInstant(String text) throws ParseException {
		return DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}
}
