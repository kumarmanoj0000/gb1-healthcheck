package com.gb1.healthcheck.services.meals;

import com.gb1.healthcheck.domain.meals.PreparationMethod;

public interface PreparedFoodCreationRequest {
	Long getIngredientId();

	PreparationMethod getPreparationMethod();
}
