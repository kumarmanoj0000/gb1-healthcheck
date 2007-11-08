package com.gb1.healthcheck.domain.foods;

public interface ComplexFoodValidator {
	void validate(ComplexFood food) throws FoodException;
}
