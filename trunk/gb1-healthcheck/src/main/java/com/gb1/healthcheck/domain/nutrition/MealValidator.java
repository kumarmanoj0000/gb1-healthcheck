package com.gb1.healthcheck.domain.nutrition;

public interface MealValidator {
	void validate(Meal meal) throws MealException;
}
