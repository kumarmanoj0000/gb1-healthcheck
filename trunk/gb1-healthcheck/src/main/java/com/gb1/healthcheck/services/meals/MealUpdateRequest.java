package com.gb1.healthcheck.services.meals;

import java.util.Date;
import java.util.Set;

public interface MealUpdateRequest {
	Date getInstant();

	Set<PreparedFoodUpdateRequest> getDishUpdateRequests();
}
