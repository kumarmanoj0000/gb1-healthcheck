package com.gb1.healthcheck.domain.nutrition;

public class ExposedSimpleFood extends SimpleFood {
	public ExposedSimpleFood(Long id, String name, FoodGroup foodGroup) {
		super(name, foodGroup);
		setId(id);
	}
}
