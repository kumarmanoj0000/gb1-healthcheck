package com.gb1.healthcheck.domain.nutrition;

import java.util.List;

public interface MealRepository {
	List<Meal> loadMeals();
}
