package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface ComplexFoodUpdateRequest {
	String getName();

	Set<Long> getIngredientIds();
}
