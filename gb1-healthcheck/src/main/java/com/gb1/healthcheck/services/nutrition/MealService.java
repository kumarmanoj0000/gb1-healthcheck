package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import com.gb1.healthcheck.domain.nutrition.Meal;

public interface MealService {
	/**
	 * Returns all recorded meals, sorted chronologically.
	 * 
	 * @return All meals sorted chronologically
	 */
	List<Meal> getMealHistory();
}
