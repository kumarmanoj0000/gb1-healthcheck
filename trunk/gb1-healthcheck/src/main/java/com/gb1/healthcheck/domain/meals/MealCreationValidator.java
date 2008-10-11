package com.gb1.healthcheck.domain.meals;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("mealCreationValidator")
public class MealCreationValidator implements Validator<Meal, MealException> {
	@Resource
	protected MealRepository mealRepo;

	public MealCreationValidator() {
	}

	public void validate(Meal meal) throws MealException {
		if (mealAlreadyExists(meal)) {
			throw new MealAlreadyExistsException();
		}
		if (mealHasNoDishes(meal)) {
			throw new MealHasNoDishesException();
		}
	}

	private boolean mealAlreadyExists(Meal meal) {
		List<Meal> mealsOnSameInstant = mealRepo.findMeals(meal.getEater(), meal.getInstant());
		return !mealsOnSameInstant.isEmpty();
	}

	private boolean mealHasNoDishes(Meal meal) {
		return meal.getDishes().isEmpty();
	}
}
