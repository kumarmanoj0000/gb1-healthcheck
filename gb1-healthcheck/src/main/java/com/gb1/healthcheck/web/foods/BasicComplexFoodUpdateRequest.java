package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.services.foods.ComplexFoodUpdateRequest;

public class BasicComplexFoodUpdateRequest extends ComplexFoodRequestSupport implements
		ComplexFoodUpdateRequest {
	public BasicComplexFoodUpdateRequest(ComplexFood food) {
		setName(food.getName());
		setIngredients(food.getIngredients());
	}
}
