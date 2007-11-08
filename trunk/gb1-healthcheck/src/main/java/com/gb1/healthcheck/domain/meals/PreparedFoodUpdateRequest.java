package com.gb1.healthcheck.domain.meals;

public interface PreparedFoodUpdateRequest {
	Long getIngredientId();

	PreparationMethod getPreparationMethod();
}
