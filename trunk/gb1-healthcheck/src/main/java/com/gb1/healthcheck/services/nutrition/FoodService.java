package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodMutablePropertyProvider;
import com.gb1.healthcheck.domain.nutrition.ComplexFoodPropertyProvider;
import com.gb1.healthcheck.domain.nutrition.FoodException;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodCreationRequest;
import com.gb1.healthcheck.domain.nutrition.SimpleFoodUpdateRequest;

public interface FoodService {
	SimpleFood loadSimpleFood(Long foodId);

	ComplexFood loadComplexFood(Long foodId, Hydrater<ComplexFood> hydrater);

	Set<SimpleFood> getSimpleFoods();

	Set<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater);

	void createSimpleFood(SimpleFoodCreationRequest request) throws FoodException;

	void createComplexFood(ComplexFoodPropertyProvider propertyProvider) throws FoodException;

	void updateSimpleFood(Long foodId, SimpleFoodUpdateRequest request) throws FoodException;

	void updateComplexFood(Long foodId, ComplexFoodMutablePropertyProvider propertyProvider)
			throws FoodException;

	void deleteFood(Long foodId);
}
