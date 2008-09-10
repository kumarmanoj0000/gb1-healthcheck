package com.gb1.healthcheck.services.meals;

import com.gb1.healthcheck.domain.meals.Meal;

public interface MealAssembler {
	Meal createMeal(MealCreationRequest request);

	void updateMeal(Meal meal, MealUpdateRequest request);
}
