package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodUpdateRequest;

public class BasicComplexFoodUpdateRequest extends ComplexFoodRequestSupport implements
		ComplexFoodUpdateRequest {
	public BasicComplexFoodUpdateRequest(ComplexFood food) {
		setName(food.getName());
		setIngredients(food.getIngredients());
	}
}
