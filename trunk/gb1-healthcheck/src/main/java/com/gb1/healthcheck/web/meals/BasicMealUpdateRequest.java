package com.gb1.healthcheck.web.meals;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.meals.Meal;
import com.gb1.healthcheck.services.meals.MealUpdateRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodUpdateRequest;

public class BasicMealUpdateRequest extends MealRequestSupport implements MealUpdateRequest {
	public BasicMealUpdateRequest() {
	}

	public BasicMealUpdateRequest(Meal meal) {
		super(meal);
	}

	public Set<PreparedFoodUpdateRequest> getDishUpdateRequests() {
		Set<PreparedFoodUpdateRequest> requests = new HashSet<PreparedFoodUpdateRequest>(
				getDishes().values());
		return requests;
	}
}
