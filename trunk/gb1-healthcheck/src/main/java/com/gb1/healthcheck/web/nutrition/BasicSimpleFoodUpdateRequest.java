package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodUpdateRequest;

public class BasicSimpleFoodUpdateRequest extends SimpleFoodRequestSupport implements
		SimpleFoodUpdateRequest {
	public BasicSimpleFoodUpdateRequest(SimpleFood food) {
		setName(food.getName());
		setGroup(food.getFoodGroup());
		setNutrients(food.getNutrients());
	}
}
