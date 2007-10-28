package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface ComplexFoodCreationRequest {
	String getName();

	Set<Long> getIngredientIds();
}
