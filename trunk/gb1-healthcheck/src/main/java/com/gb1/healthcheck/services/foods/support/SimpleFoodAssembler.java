package com.gb1.healthcheck.services.foods.support;

import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.services.foods.SimpleFoodCreationRequest;
import com.gb1.healthcheck.services.foods.SimpleFoodUpdateRequest;

public interface SimpleFoodAssembler {
	SimpleFood create(SimpleFoodCreationRequest request);

	void update(SimpleFood food, SimpleFoodUpdateRequest request);
}
