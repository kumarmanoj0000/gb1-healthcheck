package com.gb1.healthcheck.services.foods;

import java.util.Set;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;

public interface FoodService {
	SimpleFood loadSimpleFood(Long foodId);

	ComplexFood loadComplexFood(Long foodId, Hydrater<ComplexFood> hydrater);

	Set<SimpleFood> getSimpleFoods();

	Set<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater);

	void createSimpleFood(SimpleFoodCreationRequest request) throws FoodException;

	void createComplexFood(ComplexFoodCreationRequest request) throws FoodException;

	void updateSimpleFood(SimpleFoodUpdateRequest request) throws FoodException;

	void updateComplexFood(ComplexFoodUpdateRequest request) throws FoodException;

	void deleteFood(Long foodId);
}
