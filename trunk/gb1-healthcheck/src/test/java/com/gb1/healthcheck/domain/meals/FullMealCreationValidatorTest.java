package com.gb1.healthcheck.domain.meals;

import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.Users;

public class FullMealCreationValidatorTest {
	@Test
	public void testValidateNoDishes() throws MealException {
		Meal meal = new Meal(Users.gb(), new Date());
		List<Meal> mealsOnSameInstant = Collections.emptyList();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		FullMealCreationValidator v = new FullMealCreationValidator();
		v.setMealRepository(mealRepo);

		try {
			v.validate(meal);
			fail("Meal has no dishes");
		}
		catch (MealHasNoDishesException expected) {
		}
	}

	@Test
	public void testValidateMealAlreadyExists() throws MealException {
		Meal meal = Meals.fullItalianDinner();
		List<Meal> mealsOnSameInstant = Collections.singletonList(meal);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		FullMealCreationValidator v = new FullMealCreationValidator();
		v.setMealRepository(mealRepo);

		try {
			v.validate(meal);
			fail("Meal already exists");
		}
		catch (MealAlreadyExistsException expected) {
		}
	}

	@Test
	public void testValidateMealOk() throws MealException {
		Meal meal = Meals.fullItalianDinner();
		List<Meal> mealsOnSameInstant = Collections.emptyList();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		FullMealCreationValidator v = new FullMealCreationValidator();
		v.setMealRepository(mealRepo);

		v.validate(meal);
	}
}
