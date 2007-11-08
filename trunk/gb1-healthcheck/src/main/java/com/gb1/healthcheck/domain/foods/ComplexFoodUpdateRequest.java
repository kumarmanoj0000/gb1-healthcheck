package com.gb1.healthcheck.domain.foods;

import java.util.Set;

public interface ComplexFoodUpdateRequest {
	String getName();

	Set<Long> getIngredientIds();
}
