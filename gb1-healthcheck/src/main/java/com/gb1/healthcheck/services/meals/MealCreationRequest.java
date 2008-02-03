package com.gb1.healthcheck.services.meals;

import java.util.Date;
import java.util.Set;

public interface MealCreationRequest {
	Long getEaterId();

	Date getInstant();

	Set<PreparedFoodCreationRequest> getDishCreationRequests();
}
