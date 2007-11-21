package com.gb1.healthcheck.services.foods;

import java.util.Set;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.ComplexFoodCreationRequest;
import com.gb1.healthcheck.domain.foods.ComplexFoodUpdateRequest;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;
import com.gb1.healthcheck.domain.foods.SimpleFoodCreationRequest;
import com.gb1.healthcheck.domain.foods.SimpleFoodUpdateRequest;

public interface FoodService {
	SimpleFood loadSimpleFood(Long foodId);

	ComplexFood loadComplexFood(Long foodId, Hydrater<ComplexFood> hydrater);

	Set<SimpleFood> getSimpleFoods();

	Set<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater);

	void createSimpleFood(SimpleFoodCreationRequest request) throws FoodException;

	void createComplexFood(ComplexFoodCreationRequest request) throws FoodException;

	void updateSimpleFood(Long foodId, SimpleFoodUpdateRequest request) throws FoodException;

	void updateComplexFood(Long foodId, ComplexFoodUpdateRequest request) throws FoodException;

	void deleteFood(Long foodId);
}