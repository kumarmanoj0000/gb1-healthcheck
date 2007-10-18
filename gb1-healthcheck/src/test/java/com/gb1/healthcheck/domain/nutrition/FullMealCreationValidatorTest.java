package com.gb1.healthcheck.domain.nutrition;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

public class FullMealCreationValidatorTest extends TestCase {
	public void testValidateNoDishes() throws MealException {
		Meal meal = new Meal(new Date());
		List<Meal> mealsOnSameDateAndTime = Collections.emptyList();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsByDateAndTime(meal.getDateAndTime())).andReturn(
				mealsOnSameDateAndTime);
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

	public void testValidateMealAlreadyExists() throws MealException {
		Meal meal = new Meal(new Date());
		meal.addDish(Meals.spaghettiDish());

		List<Meal> mealsOnSameDateAndTime = Collections.singletonList(meal);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsByDateAndTime(meal.getDateAndTime())).andReturn(
				mealsOnSameDateAndTime);
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

	public void testValidateMealOk() throws MealException {
		Meal meal = new Meal(new Date());
		meal.addDish(Meals.spaghettiDish());

		List<Meal> mealsOnSameDateAndTime = Collections.emptyList();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsByDateAndTime(meal.getDateAndTime())).andReturn(
				mealsOnSameDateAndTime);
		EasyMock.replay(mealRepo);

		FullMealCreationValidator v = new FullMealCreationValidator();
		v.setMealRepository(mealRepo);

		v.validate(meal);
	}
}