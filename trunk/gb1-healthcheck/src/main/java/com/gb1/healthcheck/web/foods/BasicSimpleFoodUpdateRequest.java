package com.gb1.healthcheck.web.foods;

import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodUpdateRequest;

public class BasicSimpleFoodUpdateRequest extends SimpleFoodRequestSupport implements
		SimpleFoodUpdateRequest {
	public BasicSimpleFoodUpdateRequest(SimpleFood food) {
		setName(food.getName());
		setGroup(food.getFoodGroup());
		setNutrients(food.getNutrients());
	}
}
