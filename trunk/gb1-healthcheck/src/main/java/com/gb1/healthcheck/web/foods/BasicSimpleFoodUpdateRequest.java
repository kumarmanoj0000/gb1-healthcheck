package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.SimpleFoodUpdateRequest;

public class BasicSimpleFoodUpdateRequest extends SimpleFoodRequestSupport implements
		SimpleFoodUpdateRequest {
	private Long foodId;

	public BasicSimpleFoodUpdateRequest(SimpleFood food) {
		this.foodId = food.getId();
		setName(food.getName());
		setFoodGroup(food.getFoodGroup());
		setNutrients(food.getNutrients());
	}

	public Long getFoodId() {
		return foodId;
	}
}
