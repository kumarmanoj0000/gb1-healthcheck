package com.gb1.healthcheck.services.foods;

import java.util.List;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.foods.ComplexFood;
import com.gb1.healthcheck.domain.foods.Food;
import com.gb1.healthcheck.domain.foods.FoodException;
import com.gb1.healthcheck.domain.foods.SimpleFood;

public interface FoodService {
	SimpleFood getSimpleFood(Long foodId);

	ComplexFood getComplexFood(Long foodId, Hydrater<ComplexFood> hydrater);

	List<SimpleFood> getSimpleFoods();

	List<ComplexFood> getComplexFoods(Hydrater<ComplexFood> hydrater);

	Food getFood(Long foodId);

	List<Food> getFoods(List<Long> foodIds);

	void createSimpleFood(SimpleFood food) throws FoodException;

	void createComplexFood(ComplexFood food) throws FoodException;

	void updateSimpleFood(SimpleFood food) throws FoodException;

	void updateComplexFood(ComplexFood food) throws FoodException;

	void deleteFoods(List<Long> foodIds);
}
