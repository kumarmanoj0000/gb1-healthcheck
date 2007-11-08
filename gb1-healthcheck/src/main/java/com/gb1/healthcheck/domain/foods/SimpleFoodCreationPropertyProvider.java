package com.gb1.healthcheck.domain.foods;

import java.util.Set;

public interface SimpleFoodCreationPropertyProvider {
	String getName();

	FoodGroup getFoodGroup();

	Set<Nutrient> getNutrients();
}
