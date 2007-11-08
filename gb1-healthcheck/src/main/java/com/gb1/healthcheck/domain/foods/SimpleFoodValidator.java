package com.gb1.healthcheck.domain.foods;

public interface SimpleFoodValidator {
	void validate(SimpleFood food) throws FoodException;
}
