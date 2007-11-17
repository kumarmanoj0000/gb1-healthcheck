package com.gb1.healthcheck.domain.meals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import com.gb1.healthcheck.domain.users.Users;

public class FullMealUpdateValidatorTest extends TestCase {
	public void testValidate() throws MealException {
		Meal meal = Meals.fullItalianDinner();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				Collections.singletonList(meal));
		EasyMock.replay(mealRepo);

		FullMealUpdateValidator v = new FullMealUpdateValidator();
		v.setMealRepository(mealRepo);
		v.validate(meal);
	}

	public void testValidateSameInstant() throws MealException {
		Meal meal = Meals.fullItalianDinner();

		List<Meal> mealsForInstant = new ArrayList<Meal>();
		mealsForInstant.add(meal);
		mealsForInstant.add(meal);

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				mealsForInstant);
		EasyMock.replay(mealRepo);

		FullMealUpdateValidator v = new FullMealUpdateValidator();
		v.setMealRepository(mealRepo);

		try {
			v.validate(meal);
			fail("Meal already exists");
		}
		catch (MealAlreadyExistsException expected) {
		}
	}

	public void testValidateNoDishes() throws MealException {
		Meal meal = new Meal(Users.gb(), new Date());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				Collections.singletonList(meal));
		EasyMock.replay(mealRepo);

		FullMealUpdateValidator v = new FullMealUpdateValidator();
		v.setMealRepository(mealRepo);

		try {
			v.validate(meal);
			fail("Meal has no dishes");
		}
		catch (MealHasNoDishesException expected) {
		}
	}
}
