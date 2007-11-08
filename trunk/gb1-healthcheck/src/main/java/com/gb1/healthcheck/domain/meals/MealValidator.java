package com.gb1.healthcheck.domain.meals;

public interface MealValidator {
	void validate(Meal meal) throws MealException;
}
