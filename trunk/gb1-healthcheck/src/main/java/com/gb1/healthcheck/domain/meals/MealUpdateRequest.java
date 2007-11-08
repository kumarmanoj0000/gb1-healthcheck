package com.gb1.healthcheck.domain.meals;

import java.util.Date;
import java.util.Set;

public interface MealUpdateRequest {
	Date getInstant();

	Set<PreparedFoodUpdateRequest> getDishUpdateRequests();
}
