package com.gb1.healthcheck.domain.nutrition;

public interface SimpleFoodCreationValidator {
	void validate(SimpleFood food) throws FoodException;
}
