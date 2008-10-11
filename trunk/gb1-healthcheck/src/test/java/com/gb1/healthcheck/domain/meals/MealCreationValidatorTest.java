package com.gb1.healthcheck.domain.meals;

import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.Users;

public class MealCreationValidatorTest {
	@Test
	public void testValidateNoDishes() throws MealException {
		Meal meal = new Meal();
		meal.setEater(Users.gb());
		List<Meal> mealsOnSameInstant = Collections.emptyList();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMeals(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		MealCreationValidator v = new MealCreationValidator();
		v.mealRepo = mealRepo;

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
		EasyMock.expect(mealRepo.findMeals(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		MealCreationValidator v = new MealCreationValidator();
		v.mealRepo = mealRepo;

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
		EasyMock.expect(mealRepo.findMeals(meal.getEater(), meal.getInstant())).andReturn(
				mealsOnSameInstant);
		EasyMock.replay(mealRepo);

		MealCreationValidator v = new MealCreationValidator();
		v.mealRepo = mealRepo;

		v.validate(meal);
	}
}
