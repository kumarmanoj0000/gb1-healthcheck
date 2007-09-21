package com.gb1.healthcheck.services.nutrition;

import java.util.Set;

import com.gb1.healthcheck.domain.nutrition.ComplexFood;
import com.gb1.healthcheck.domain.nutrition.SimpleFood;

public interface FoodService {
	Set<SimpleFood> getSimpleFoods();

	Set<ComplexFood> getComplexFoods();
}
