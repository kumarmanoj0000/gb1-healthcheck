package com.gb1.healthcheck.domain.foods;

import java.util.List;

public interface FoodRepository {
	Food loadFood(Long foodId);

	SimpleFood loadSimpleFood(Long foodId);

	ComplexFood loadComplexFood(Long foodId);

	Food findFoodByName(String name);

	List<Food> findFoodsByName(String name);

	List<SimpleFood> findSimpleFoods();

	List<ComplexFood> findComplexFoods();

	void saveFood(Food food);

	void deleteFood(Long foodId);
}
