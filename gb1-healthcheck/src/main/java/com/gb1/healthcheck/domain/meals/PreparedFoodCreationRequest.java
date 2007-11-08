package com.gb1.healthcheck.domain.meals;

public interface PreparedFoodCreationRequest {
	Long getIngredientId();

	PreparationMethod getPreparationMethod();
}
