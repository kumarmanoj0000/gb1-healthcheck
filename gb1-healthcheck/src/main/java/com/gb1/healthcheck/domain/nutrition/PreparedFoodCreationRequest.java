package com.gb1.healthcheck.domain.nutrition;

public interface PreparedFoodCreationRequest {
	Long getIngredientId();

	PreparationMethod getPreparationMethod();
}
