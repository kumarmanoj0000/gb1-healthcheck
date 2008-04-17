package com.gb1.healthcheck.web.meals;

import com.gb1.healthcheck.domain.meals.PreparationMethod;
import com.gb1.healthcheck.services.meals.PreparedFoodCreationRequest;
import com.gb1.healthcheck.services.meals.PreparedFoodUpdateRequest;

class BasicPreparedFoodRequest implements PreparedFoodCreationRequest, PreparedFoodUpdateRequest {
	private Long ingredientId;
	private PreparationMethod prepMethod;

	public BasicPreparedFoodRequest() {
	}

	public BasicPreparedFoodRequest(Long ingredientId, PreparationMethod prepMethod) {
		this.ingredientId = ingredientId;
		this.prepMethod = prepMethod;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public PreparationMethod getPreparationMethod() {
		return prepMethod;
	}

	public String getPreparationMethodName() {
		return prepMethod.name();
	}

	public void setPreparationMethodName(String prepMethodName) {
		prepMethod = PreparationMethod.valueOf(prepMethodName);
	}
}
