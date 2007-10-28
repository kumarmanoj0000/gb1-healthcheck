package com.gb1.healthcheck.domain.nutrition;

import java.util.Date;
import java.util.Set;

public interface MealCreationRequest {
	Date getInstant();

	Set<PreparedFoodCreationRequest> getDishCreationRequests();
}
