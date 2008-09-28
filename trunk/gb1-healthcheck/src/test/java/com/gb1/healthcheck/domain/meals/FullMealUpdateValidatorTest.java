package com.gb1.healthcheck.domain.meals;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import com.gb1.healthcheck.domain.users.Users;

public class FullMealUpdateValidatorTest {
	@Test
	public void testValidate() throws MealException {
		Meal meal = Meals.fullItalianDinner();

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				Collections.singletonList(meal));
		EasyMock.replay(mealRepo);

		FullMealUpdateValidator v = new FullMealUpdateValidator();
		v.mealRepo = mealRepo;
		v.validate(meal);
	}

	@Test
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
		v.mealRepo = mealRepo;

		try {
			v.validate(meal);
			fail("Meal already exists");
		}
		catch (MealAlreadyExistsException expected) {
		}
	}

	@Test
	public void testValidateNoDishes() throws MealException {
		Meal meal = new Meal(Users.gb());

		MealRepository mealRepo = EasyMock.createMock(MealRepository.class);
		EasyMock.expect(mealRepo.findMealsBy(meal.getEater(), meal.getInstant())).andReturn(
				Collections.singletonList(meal));
		EasyMock.replay(mealRepo);

		FullMealUpdateValidator v = new FullMealUpdateValidator();
		v.mealRepo = mealRepo;

		try {
			v.validate(meal);
			fail("Meal has no dishes");
		}
		catch (MealHasNoDishesException expected) {
		}
	}
}
