package com.gb1.healthcheck.web.nutrition;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealUpdateRequest;
import com.gb1.healthcheck.domain.nutrition.PreparedFoodUpdateRequest;

public class BasicMealUpdateRequest extends MealRequestSupport implements MealUpdateRequest {
	public BasicMealUpdateRequest(Meal meal) {
		setInstant(meal.getInstant());
		setDishes(meal.getDishes());
	}

	public Set<PreparedFoodUpdateRequest> getPreparedFoodUpdateRequests() {
		Set<PreparedFoodUpdateRequest> requests = new HashSet<PreparedFoodUpdateRequest>();
		return requests;
	}
}
