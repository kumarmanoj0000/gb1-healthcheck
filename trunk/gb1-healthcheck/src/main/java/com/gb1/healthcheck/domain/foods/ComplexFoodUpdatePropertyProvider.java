package com.gb1.healthcheck.domain.foods;

import java.util.Set;

public interface ComplexFoodUpdatePropertyProvider {
	String getName();

	Set<Food> getIngredients();
}
