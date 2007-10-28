package com.gb1.healthcheck.web.nutrition;

import java.util.HashSet;
import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.MealCreationRequest;
import com.gb1.healthcheck.domain.nutrition.PreparedFoodCreationRequest;

public class BasicMealCreationRequest extends MealRequestSupport implements MealCreationRequest {
	public BasicMealCreationRequest() {
	}

	public Set<PreparedFoodCreationRequest> getPreparedFoodCreationRequests() {
		Set<PreparedFoodCreationRequest> requests = new HashSet<PreparedFoodCreationRequest>();
		return requests;
	}
}
