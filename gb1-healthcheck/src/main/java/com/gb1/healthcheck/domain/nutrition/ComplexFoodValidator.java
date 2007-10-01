package com.gb1.healthcheck.domain.nutrition;

public interface ComplexFoodValidator {
	void validate(ComplexFood food) throws FoodException;
}
