package com.gb1.healthcheck.services.foods;

import java.util.List;

import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;

public interface FoodService {
	SimpleFood findSimpleFood(Long foodId);

	ComplexFood findComplexFood(Long foodId);

	List<SimpleFood> findAllSimpleFoods();

	List<ComplexFood> findAllComplexFoods();

	Food findFood(Long foodId);

	List<Food> findFoods(List<Long> foodIds);

	void createSimpleFood(SimpleFood food) throws FoodException;

	void createComplexFood(ComplexFood food) throws FoodException;

	void updateSimpleFood(SimpleFood food) throws FoodException;

	void updateComplexFood(ComplexFood food) throws FoodException;

	void deleteFoods(List<Long> foodIds);
}
