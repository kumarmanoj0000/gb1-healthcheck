package com.gb1.healthcheck.services.foods;

import com.gb1.healthcheck.domain.foods.ComplexFood;

public interface ComplexFoodAssembler {
	ComplexFood create(ComplexFoodCreationRequest request);

	void update(ComplexFood food, ComplexFoodUpdateRequest request);
}
