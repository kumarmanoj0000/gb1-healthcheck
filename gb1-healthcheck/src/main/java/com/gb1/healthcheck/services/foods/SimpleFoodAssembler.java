package com.gb1.healthcheck.services.foods;

import com.gb1.healthcheck.domain.foods.SimpleFood;

public interface SimpleFoodAssembler {
	SimpleFood create(SimpleFoodCreationRequest request);

	void update(SimpleFood food, SimpleFoodUpdateRequest request);
}
