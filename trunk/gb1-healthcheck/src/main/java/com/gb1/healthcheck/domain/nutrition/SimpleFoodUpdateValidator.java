package com.gb1.healthcheck.domain.nutrition;

public interface SimpleFoodUpdateValidator {
	void validate(SimpleFood food) throws FoodException;
}
