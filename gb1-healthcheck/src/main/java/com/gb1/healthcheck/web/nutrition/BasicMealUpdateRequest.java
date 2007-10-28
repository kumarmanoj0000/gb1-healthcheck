package com.gb1.healthcheck.web.nutrition;

import com.gb1.healthcheck.domain.nutrition.Meal;

public class BasicMealUpdateRequest extends MealRequestSupport {
	public BasicMealUpdateRequest(Meal meal) {
		setInstant(meal.getInstant());
		setDishes(meal.getDishes());
	}
}
