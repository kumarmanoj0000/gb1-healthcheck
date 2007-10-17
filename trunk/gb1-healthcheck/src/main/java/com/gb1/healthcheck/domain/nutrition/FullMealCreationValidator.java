package com.gb1.healthcheck.domain.nutrition;

import java.util.List;

public class FullMealCreationValidator implements MealValidator {
	private MealRepository mealRepo;

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
		List<Meal> mealsOnSameDateAndTime = mealRepo.findMealsByDateAndTime(meal.getDateAndTime());
		return !mealsOnSameDateAndTime.isEmpty();
	}

	private boolean mealHasNoDishes(Meal meal) {
		return meal.getDishes().isEmpty();
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}
}
