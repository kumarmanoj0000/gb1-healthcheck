package com.gb1.healthcheck.services.meals;

import java.util.Date;
import java.util.Set;

public interface MealUpdateRequest {
	Long getMealId();

	Date getInstant();

	Set<PreparedFoodUpdateRequest> getDishUpdateRequests();
}
