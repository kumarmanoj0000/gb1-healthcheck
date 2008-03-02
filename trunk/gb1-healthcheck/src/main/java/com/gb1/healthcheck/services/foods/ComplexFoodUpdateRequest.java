package com.gb1.healthcheck.services.foods;

import java.util.Set;

public interface ComplexFoodUpdateRequest {
	Long getFoodId();

	String getName();

	Set<Long> getIngredientIds();
}
