package com.gb1.healthcheck.services.foods.support;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.services.foods.ComplexFoodCreationRequest;
import com.gb1.healthcheck.services.foods.ComplexFoodUpdateRequest;

public interface ComplexFoodAssembler {
	ComplexFood create(ComplexFoodCreationRequest request);

	void update(ComplexFood food, ComplexFoodUpdateRequest request);
}
