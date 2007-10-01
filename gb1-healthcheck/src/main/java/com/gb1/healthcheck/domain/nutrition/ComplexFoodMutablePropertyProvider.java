package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface ComplexFoodMutablePropertyProvider {
	String getName();

	Set<Food> getIngredients();
}
