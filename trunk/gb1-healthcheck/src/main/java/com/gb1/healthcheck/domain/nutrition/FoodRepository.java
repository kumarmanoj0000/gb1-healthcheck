package com.gb1.healthcheck.domain.nutrition;

import java.util.Set;

public interface FoodRepository {
	Food loadFood(Long id);

	Food findFoodByName(String name);

	Set<SimpleFood> findSimpleFoods();

	Set<ComplexFood> findComplexFoods();
}
