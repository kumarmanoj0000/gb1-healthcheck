package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.SimpleFood;

public class SimpleFoodUpdateRequest extends SimpleFoodRequestSupport {
	public SimpleFoodUpdateRequest(SimpleFood food) {
		setName(food.getName());
		setGroup(food.getFoodGroup());
		setNutrients(food.getNutrients());
	}
}
