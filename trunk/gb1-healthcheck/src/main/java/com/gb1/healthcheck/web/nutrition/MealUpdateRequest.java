package com.gb1.healthcheck.web.nutrition;

import org.springframework.beans.factory.annotation.Configurable;

import com.gb1.healthcheck.domain.nutrition.Meal;

@Configurable("mealUpdateRequest")
public class MealUpdateRequest extends MealRequestSupport {
	public MealUpdateRequest(Meal meal) {
		setInstant(meal.getInstant());
	}
}
