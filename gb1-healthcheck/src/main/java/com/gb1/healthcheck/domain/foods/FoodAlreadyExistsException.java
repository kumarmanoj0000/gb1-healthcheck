package com.gb1.healthcheck.domain.foods;

public class FoodAlreadyExistsException extends FoodException {
	private String foodName;

	public FoodAlreadyExistsException(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodName() {
		return foodName;
	}
}
