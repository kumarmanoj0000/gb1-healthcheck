package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface SimpleFoodMutablePropertyProvider {
	String getName();

	FoodGroup getFoodGroup();

	Set<Nutrient> getNutrients();
}
