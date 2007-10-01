package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodPropertyProvider;

public interface FoodService {
	SimpleFood loadSimpleFood(Long foodId);

	Set<SimpleFood> getSimpleFoods();

	Set<ComplexFood> getComplexFoods();

	void createSimpleFood(SimpleFoodPropertyProvider propertyProvider) throws FoodException;

	void updateSimpleFood(Long foodId, SimpleFoodMutablePropertyProvider propertyProvider)
			throws FoodException;

	void deleteSimpleFood(Long foodId);
}
