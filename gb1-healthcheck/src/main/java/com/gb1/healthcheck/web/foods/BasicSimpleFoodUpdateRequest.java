package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.SimpleFoodUpdateRequest;

public class BasicSimpleFoodUpdateRequest extends SimpleFoodRequestSupport implements
		SimpleFoodUpdateRequest {
	public BasicSimpleFoodUpdateRequest(SimpleFood food) {
		setName(food.getName());
		setFoodGroup(food.getFoodGroup());
		setNutrients(food.getNutrients());
	}
}
