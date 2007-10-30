package com.gb1.healthcheck.services.nutrition;

import java.util.List;

import com.gb1.commons.dataaccess.Hydrater;
import com.gb1.healthcheck.domain.nutrition.Meal;
import com.gb1.healthcheck.domain.nutrition.MealCreationRequest;
import com.gb1.healthcheck.domain.nutrition.MealException;
import com.gb1.healthcheck.domain.nutrition.MealUpdateRequest;

public interface MealService {
	/**
	 * Returns all recorded meals, sorted chronologically.
	 * 
	 * @return All meals sorted chronologically
	 */
	List<Meal> getMealHistory();

	/**
	 * Loads an existing meal identified by a given ID.
	 * 
	 * @param mealId The ID of the meal to load
	 * @param hydrater The callback to hydrate meal associations
	 * @return The corresponding meal; null if non existent
	 */
	Meal loadMeal(Long mealId, Hydrater<Meal> hydrater);

	/**
	 * Creates a new meal, based on a creation request.
	 * 
	 * @param request The creation request
	 * @throws MealException When creation fails
	 */
	void createMeal(MealCreationRequest request) throws MealException;

	/**
	 * Updates an existing meal, based on an update request.
	 * 
	 * @param mealId The ID of the meal to modify
	 * @param request The update request
	 * @throws MealException When update fails
	 */
	void updateMeal(Long mealId, MealUpdateRequest request) throws MealException;

	/**
	 * Deletes the meal identified by the given ID.
	 * 
	 * @param mealId The ID of the meal to delete
	 */
	void deleteMeal(Long mealId);
}
