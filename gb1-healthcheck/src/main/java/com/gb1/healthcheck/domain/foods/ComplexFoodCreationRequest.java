package com.gb1.healthcheck.domain.foods;

import java.util.Set;

public interface ComplexFoodCreationRequest {
	String getName();

	Set<Long> getIngredientIds();
}
