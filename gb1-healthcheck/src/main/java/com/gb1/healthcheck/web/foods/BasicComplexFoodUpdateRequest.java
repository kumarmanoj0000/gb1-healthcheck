package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.services.foods.ComplexFoodUpdateRequest;

public class BasicComplexFoodUpdateRequest extends ComplexFoodRequestSupport implements
		ComplexFoodUpdateRequest {
	private Long foodId;

	public BasicComplexFoodUpdateRequest(ComplexFood food) {
		this.foodId = food.getId();
		setName(food.getName());
		setIngredients(food.getIngredients());
	}

	public Long getFoodId() {
		return foodId;
	}
}
