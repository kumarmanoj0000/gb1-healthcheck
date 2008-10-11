package com.gb1.healthcheck.domain.meals;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gb1.healthcheck.core.Validator;

@Component("mealCreationValidator")
public class FullMealCreationValidator implements Validator<Meal, MealException> {
	@Resource
	protected MealRepository mealRepo;

	public FullMealCreationValidator() {
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
		List<Meal> mealsOnSameInstant = mealRepo.findMealsBy(meal.getEater(), meal.getInstant());
		return !mealsOnSameInstant.isEmpty();
	}

	private boolean mealHasNoDishes(Meal meal) {
		return meal.getDishes().isEmpty();
	}
}
