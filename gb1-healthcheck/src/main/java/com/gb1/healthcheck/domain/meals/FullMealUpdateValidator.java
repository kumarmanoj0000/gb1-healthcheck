package com.gb1.healthcheck.domain.meals;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("mealUpdateValidator")
public class FullMealUpdateValidator implements MealValidator {
	@Resource
	protected MealRepository mealRepo;

	public FullMealUpdateValidator() {
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
		boolean exists;
		List<Meal> mealsForInstant = mealRepo.findMealsBy(meal.getEater(), meal.getInstant());

		if (mealsForInstant.isEmpty()) {
			exists = false;
		}
		else if (mealsForInstant.size() == 1) {
			exists = (mealsForInstant.get(0).equals(meal) ? false : true);
		}
		else {
			exists = true;
		}

		return exists;
	}

	private boolean mealHasNoDishes(Meal meal) {
		return meal.getDishes().isEmpty();
	}
}
