package com.gb1.healthcheck.services.foods;

import java.util.Set;

public interface ComplexFoodUpdateRequest {
	String getName();

	Set<Long> getIngredientIds();
}
