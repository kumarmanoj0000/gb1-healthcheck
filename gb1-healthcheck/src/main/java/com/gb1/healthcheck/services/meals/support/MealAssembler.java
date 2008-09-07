package com.gb1.healthcheck.services.meals.support;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.services.meals.MealCreationRequest;
import com.gb1.healthcheck.services.meals.MealUpdateRequest;

public interface MealAssembler {
	Meal create(MealCreationRequest request);

	void update(Meal meal, MealUpdateRequest request);
}
