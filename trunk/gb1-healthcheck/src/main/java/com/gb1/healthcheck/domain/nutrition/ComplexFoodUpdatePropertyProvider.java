package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface ComplexFoodUpdatePropertyProvider {
	String getName();

	Set<Food> getIngredients();
}
