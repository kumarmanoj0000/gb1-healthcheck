package com.gb1.healthcheck.domain.meals;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.ExposedUser;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.Users;
import com.gb1.test.AbstractInMemoryPersistenceTestCase;

public class JpaMealRepositoryTest extends AbstractInMemoryPersistenceTestCase {
	@Resource
	private MealRepository mealRepo = null;

	@Test
	public void testLoadMeals() throws ParseException {
		final User eater = Users.gb();

		Meal meal1 = new Meal().setEater(eater).setInstant(parseInstant("2007-10-15 16:00"))
				.addDish(Meals.redWineDrink());
		Meal meal2 = new Meal().setEater(eater).setInstant(parseInstant("2007-10-15 18:30"))
				.addDish(Meals.redWineDrink()).addDish(Meals.spaghettiDish());

		List<Meal> meals = mealRepo.findMealsBy(eater);
		assertEquals(2, meals.size());
		assertTrue(meals.contains(meal1));
		assertTrue(meals.contains(meal2));
	}

	@Test
	public void testFindMealsByInstant() throws ParseException {
		final User eater = Users.lg();
		Date mealInstant = parseInstant("2007-10-16 18:00");
		Meal meal = new Meal().setEater(eater).setInstant(mealInstant).addDish(
				Meals.spaghettiDish());

		List<Meal> meals = mealRepo.findMealsBy(eater, mealInstant);

		assertEquals(1, meals.size());
		assertTrue(meals.contains(meal));
	}

	@Test
	public void testSaveMeal() throws ParseException {
		Meal meal = new Meal().setEater(Users.lg()).setInstant(parseInstant("2007-10-16 18:00"))
				.addDish(Meals.spaghettiDish());
		mealRepo.persistMeal(meal);

		assertEquals(meal, mealRepo.loadMeal(meal.getId()));
	}

	@Test
	public void testDeleteMeal() {
		Meal meal = mealRepo.loadMeal(Meals.fullItalianDinner().getId());
		mealRepo.deleteMeal(meal);
		assertNull(mealRepo.loadMeal(meal.getId()));
	}

	@Test
	public void testGetLastMealUserNeverAte() {
		ExposedUser user = new ExposedUser();
		user.setId(3L);

		assertNull(mealRepo.getLastMealBy(user));
	}

	@Test
	public void testGetLastMeal() throws Exception {
		Meal lastMeal = mealRepo.getLastMealBy(Users.gb());
		assertNotNull(lastMeal);
		assertEquals(parseInstant("2007-10-15 18:30"), lastMeal.getInstant());
	}

	private Date parseInstant(String text) throws ParseException {
		return DateUtils.parseDate(text, new String[] { "yyyy-MM-dd hh:mm" });
	}
}
