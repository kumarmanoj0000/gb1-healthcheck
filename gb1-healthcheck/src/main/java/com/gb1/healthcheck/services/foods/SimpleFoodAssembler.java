package com.gb1.healthcheck.services.foods;

import com.gb1.healthcheck.domain.foods.SimpleFood;

public interface SimpleFoodAssembler {
	SimpleFood createSimpleFood(SimpleFoodCreationRequest request);

	void updateSimpleFood(SimpleFood food, SimpleFoodUpdateRequest request);
}
