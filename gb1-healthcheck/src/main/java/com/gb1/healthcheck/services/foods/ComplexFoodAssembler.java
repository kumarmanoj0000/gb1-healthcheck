package com.gb1.healthcheck.services.foods;

import com.gb1.healthcheck.domain.foods.ComplexFood;

public interface ComplexFoodAssembler {
	ComplexFood createComplexFood(ComplexFoodCreationRequest request);

	void updateComplexFood(ComplexFood food, ComplexFoodUpdateRequest request);
}
