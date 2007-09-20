package com.gb1.healthcheck.domain.nutrition;

public interface FoodRepository {
	Food loadFood(Long id);

	Food findFoodByName(String name);
}
