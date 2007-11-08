package com.gb1.healthcheck.domain.meals;

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
		List<Meal> mealsOnSameInstant = mealRepo.findMealsByInstant(meal.getInstant());
		return !mealsOnSameInstant.isEmpty();
	}

	private boolean mealHasNoDishes(Meal meal) {
		return meal.getDishes().isEmpty();
	}

	public void setMealRepository(MealRepository mealRepo) {
		this.mealRepo = mealRepo;
	}
}
