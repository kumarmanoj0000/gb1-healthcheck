package com.gb1.healthcheck.services.meals;

import com.gb1.healthcheck.domain.meals.Meal;

public interface MealAssembler {
	Meal create(MealCreationRequest request);

	void update(Meal meal, MealUpdateRequest request);
}
