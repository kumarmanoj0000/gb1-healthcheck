package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface SimpleFoodUpdatePropertyProvider {
	String getName();

	FoodGroup getFoodGroup();

	Set<Nutrient> getNutrients();
}
