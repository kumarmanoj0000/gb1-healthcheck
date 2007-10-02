package com.gb1.healthcheck.domain.nutrition;

public interface SimpleFoodValidator {
	void validate(SimpleFood food) throws FoodException;
}
