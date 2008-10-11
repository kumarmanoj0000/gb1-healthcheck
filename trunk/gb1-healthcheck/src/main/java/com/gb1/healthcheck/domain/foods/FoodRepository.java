package com.gb1.healthcheck.domain.foods;

import java.util.List;

public interface FoodRepository {
	Food findFood(Long foodId);

	SimpleFood findSimpleFood(Long foodId);

	ComplexFood findComplexFood(Long foodId);

	Food findFoodByName(String name);

	List<Food> findFoodsByName(String name);

	List<SimpleFood> findAllSimpleFoods();

	List<ComplexFood> findAllComplexFoods();

	void persist(Food food);

	void merge(Food food);

	void delete(Food food);
}
