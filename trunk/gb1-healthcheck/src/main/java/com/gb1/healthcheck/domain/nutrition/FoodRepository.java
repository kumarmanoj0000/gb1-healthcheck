package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface FoodRepository {
	Food loadFood(Long foodId);

	SimpleFood loadSimpleFood(Long foodId);

	Food findFoodByName(String name);

	Set<Food> findFoodsByName(String name);

	Set<SimpleFood> findSimpleFoods();

	Set<ComplexFood> findComplexFoods();

	void saveFood(Food food);

	void deleteFood(Long foodId);
}
